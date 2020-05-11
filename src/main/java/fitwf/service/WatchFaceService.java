package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.exception.PermissionDeniedException;
import fitwf.exception.UserNotFoundException;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.repository.UserRepository;
import fitwf.repository.WatchFaceRepository;
import fitwf.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WatchFaceService {
    private final WatchFaceRepository watchFaceRepository;
    private final UserRepository userRepository;

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

    public void addNewWF(WatchFace watchFace) {
        /*
Процесс загрузки циферблата:
    получить .bin файл
    валидировать файл
    проверить наличие .bin:
        получить md5
        1)либо поиск по file_uri в базе данных
        2)либо поиск по директории bin файлов
        3)либо ???
        если есть файл - отдать циферблат
    сгенерировать .png превью
    сохранить файлы:
        .bin с md5 в названии в директорию бинарников
        .png превью с md5(.bin)+"preview" в названии в директорию превью
    сгенерировать описание
    сохранить циферблат в бд(юзер, preview_uri, file_uri, описание)
    вернуть ОК
*/
        watchFace.setFeatures("awesome features");
        watchFace.setFile_uri("file");
        watchFace.setPreview_uri("preview");
        JwtUser test = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        watchFace.setUser(userRepository.findByUsername(test.getUsername()).orElseThrow(() -> new UserNotFoundException("")));
        watchFaceRepository.save(watchFace);
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
        int lastId = watchFaceRepository.getLastId();
        if (lastId == offsetId - 1)
            throw new WatchFaceNotFoundException("There no more WatchFaces");
        List<WatchFace> watchFaceList = watchFaceRepository
                .getFirst3ByIdLessThanEqualAndEnabledTrueOrderByIdDesc(lastId - offsetId);
        if (watchFaceList.isEmpty())
            throw new WatchFaceNotFoundException("There no more WatchFaces");
        return watchFaceList
                .stream()
                .map(WatchFaceDTO::new)
                .collect(Collectors.toList());
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

    protected boolean checkFavorite(int userId, int wfId) {
        return watchFaceRepository.checkFavorite(userId, wfId);
    }

    protected boolean checkLike(int userId, int wfId) {
        return watchFaceRepository.checkLike(userId, wfId);
    }
}










