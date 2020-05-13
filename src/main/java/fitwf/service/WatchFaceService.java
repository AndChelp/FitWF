package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.exception.PermissionDeniedException;
import fitwf.exception.UserNotFoundException;
import fitwf.exception.WatchFaceAlreadyExistsException;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.repository.UserRepository;
import fitwf.repository.WatchFaceRepository;
import fitwf.security.RoleName;
import fitwf.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WatchFaceService {
    private final WatchFaceRepository watchFaceRepository;
    private final UserRepository userRepository;

    @Value("${upload.path.bin}")
    private String binUploadPath;

    @Value("${upload.path.gif}")
    private String gifUploadPath;

    @Autowired
    public WatchFaceService(WatchFaceRepository watchFaceRepository, UserRepository userRepository) {
        this.watchFaceRepository = watchFaceRepository;
        this.userRepository = userRepository;
    }

    public void like(int wfId) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        watchFaceRepository.like(user.getId(), wfId);
    }

    public void favorite(int wfId) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        watchFaceRepository.favorite(user.getId(), wfId);
    }

    public void addNewWatchFace(MultipartFile watchFaceBin, MultipartFile watchFaceGif) throws NoSuchAlgorithmException, IOException {
        if (watchFaceBin != null && watchFaceGif != null && watchFaceBin.getName().equals("bin")) {
            File binUploadDir = new File(binUploadPath);
            File gifUploadDir = new File(gifUploadPath);
            if (!binUploadDir.exists() || !gifUploadDir.exists()) {
                gifUploadDir.mkdirs();
                binUploadDir.mkdirs();
            }
            try (InputStream binInputStream = watchFaceBin.getInputStream()) {
                String md5Name = DigestUtils.md5DigestAsHex(binInputStream);
                String binURI = binUploadPath + md5Name + ".bin";
                File binFile = new File(binURI);
                if (binFile.exists())
                    throw new WatchFaceAlreadyExistsException("WatchFace already exists", watchFaceRepository.getIdByURI(binURI));
                String gifURI = gifUploadPath + md5Name + ".gif";
                watchFaceBin.transferTo(binFile);
                watchFaceGif.transferTo(new File(gifURI));
                JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userRepository.findByUsername(jwtUser.getUsername()).orElseThrow(() -> new UserNotFoundException(""));
                WatchFace watchFace = new WatchFace(user, md5Name + ".gif", md5Name + ".bin", "still awesome features");
                watchFaceRepository.save(watchFace);
            }
        }
    }

    private File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    public void deleteByUser(int userId) {
        watchFaceRepository.deleteByUser(userId);
    }

    public void deleteByID(int wfId) {
        WatchFace watchFace = watchFaceRepository.findById(wfId)
                .orElseThrow(() ->
                        new WatchFaceNotFoundException("WatchFace with id=" + wfId + " not found"));

        if (!watchFace.isEnabled())
            throw new PermissionDeniedException("WatchFace with id=" + wfId + " was already deleted");

        watchFaceRepository.deleteById(wfId);
    }

    public WatchFaceDTO getWatchFaceByID(int wfId) {
        return new WatchFaceDTO(
                watchFaceRepository.findById(wfId)
                        .orElseThrow(() ->
                                new WatchFaceNotFoundException("WatchFace with id=" + wfId + " not found")));
    }

    public List<WatchFaceDTO> getFiftyWatchFaces(int offsetId) {
        //TODO: удален диапазон -> возвращает один и тот же список несколько раз подряд
        boolean isAnonymous = !SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_USER.name()));
        int lastId = watchFaceRepository.getLastId();
        if (lastId == offsetId - 1)
            throw new WatchFaceNotFoundException("There no more WatchFaces");
        List<WatchFace> watchFaceList = watchFaceRepository
                .getFirst3ByIdLessThanEqualAndEnabledTrueOrderByIdDesc(lastId - offsetId);
        if (watchFaceList.isEmpty())
            throw new WatchFaceNotFoundException("There no more WatchFaces");
        List<WatchFace> enabledWatchFaces = watchFaceList.stream()
                .filter(WatchFace::isEnabled)
                .collect(Collectors.toList());
        if (isAnonymous)
            return enabledWatchFaces
                    .stream()
                    .map(WatchFaceDTO::new)
                    .collect(Collectors.toList());
        else {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return enabledWatchFaces
                    .stream()
                    .map(watchFace -> new WatchFaceDTO(
                            watchFace,
                            checkLike(jwtUser.getId(), watchFace.getId()),
                            checkFavorite(jwtUser.getId(), watchFace.getId())))
                    .collect(Collectors.toList());
        }
    }

    public List<WatchFaceDTO> getFiftyLikedWatchFaces(int offsetId) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = jwtUser.getId();
        User user = userRepository.getOne(userId);
        List<WatchFace> watchFaceList = user.getLikedWatchFaces();
        if (offsetId >= watchFaceList.size())
            throw new WatchFaceNotFoundException("There no more liked WatchFaces");
        int offsetEnd = Math.min(offsetId + /*TODO:replace 3 with 50*/3, watchFaceList.size());
        List<WatchFace> enabledWatchFaces = watchFaceList.subList(offsetId, offsetEnd).stream()
                .filter(WatchFace::isEnabled)
                .collect(Collectors.toList());
        return enabledWatchFaces
                .stream()
                .map(watchFace -> new WatchFaceDTO(
                        watchFace,
                        true,
                        checkFavorite(userId, watchFace.getId())))
                .collect(Collectors.toList());
    }

    public List<WatchFaceDTO> getFiftyFavoritedWatchFaces(int offsetId) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = jwtUser.getId();
        User user = userRepository.getOne(userId);
        List<WatchFace> watchFaceList = user.getFavoritedWatchFaces();
        if (offsetId >= watchFaceList.size())
            throw new WatchFaceNotFoundException("There no more liked WatchFaces");
        int offsetEnd = Math.min(offsetId + /*TODO:replace 3 with 50*/3, watchFaceList.size());
        List<WatchFace> enabledWatchFaces = watchFaceList.subList(offsetId, offsetEnd).stream()
                .filter(WatchFace::isEnabled)
                .collect(Collectors.toList());
        return enabledWatchFaces
                .stream()
                .map(watchFace -> new WatchFaceDTO(
                        watchFace,
                        checkLike(userId, watchFace.getId()),
                        true))
                .collect(Collectors.toList());
    }

    private boolean checkFavorite(int userId, int wfId) {
        return watchFaceRepository.checkFavorite(userId, wfId);
    }

    private boolean checkLike(int userId, int wfId) {
        return watchFaceRepository.checkLike(userId, wfId);
    }
}










