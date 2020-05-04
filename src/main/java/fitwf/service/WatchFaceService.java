package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.exception.PermissionDeniedException;
import fitwf.exception.UserNotFoundException;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.repository.RoleRepository;
import fitwf.repository.UserRepository;
import fitwf.repository.WatchFaceRepository;
import fitwf.security.RoleName;
import fitwf.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public void deleteByID(int id) {
        /*
        Нельзя удалить удаленный циферблат
        Юзер может удалить только свой циферблат
        Админ может удалить любой циферблат
        Проверка для юзера:
            1) Получить объект циферблата из бд по ID
                Сравнить владельца с юзером
                ???
                profit
            2) Создать хранимку на стороне бд
                    CHECK_OWNER(id_user, id_wf):boolean
                    if select id_user from watchfaces where id = id_wf == id_user
                    then return true
                    else return false
                    end if;
                вызвать ее
                решить по результату
                >>нужна проверка на удаленность цифера
            3)Пройтись по местной коллекции циферблатов у юзера и найти id
                >>требует FetchType.EAGER
        */
        //Вариант первый
        WatchFace watchFace = watchFaceRepository.findById(id)
                .orElseThrow(() ->
                        new WatchFaceNotFoundException("WatchFace with id=" + id + " not found"));

        if (!watchFace.isEnabled())
            throw new PermissionDeniedException("WatchFace with id=" + id + " was already deleted");

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isOwner = watchFace.getUser().getId() == jwtUser.getId();
        boolean isAdmin = jwtUser.getRoles().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()));
        if (isAdmin || isOwner)
            watchFaceRepository.deleteById(id);
        else
            throw new PermissionDeniedException("You don't have a permission to do it");
    }

    public WatchFaceDTO getWatchFaceByID(int id) {
        return new WatchFaceDTO(
                watchFaceRepository.findById(id)
                        .orElseThrow(() ->
                                new WatchFaceNotFoundException("WatchFace with id=" + id + " not found")));
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

    /*TODO: Загрузить все сразу при аутентификации или оставить как есть - ?
    Или получить через запрос в бд с условием доступности циферблата*/
    public List<WatchFace> getFiftyLikedWatchFaces(int offsetStart) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(jwtUser.getId());
        List<WatchFace> watchFaceList = user.getLikedWatchFaces();
        if (offsetStart >= watchFaceList.size())
            throw new WatchFaceNotFoundException("There no more liked WatchFaces");
        int offsetEnd = Math.min(offsetStart + /*TODO:replace 3 with 50*/3, watchFaceList.size());
        return watchFaceList.subList(offsetStart, offsetEnd).stream()
                .filter(WatchFace::isEnabled)
                .collect(Collectors.toList());
    }

    public List<WatchFace> getFiftyFavoritedWatchFaces(int offsetStart) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(jwtUser.getId());
        List<WatchFace> watchFaceList = user.getFavoritedWatchFaces();
        if (offsetStart >= watchFaceList.size())
            throw new WatchFaceNotFoundException("There no more liked WatchFaces");
        int offsetEnd = Math.min(offsetStart + /*TODO:replace 3 with 50*/3, watchFaceList.size());
        return watchFaceList.subList(offsetStart, offsetEnd).stream()
                .filter(WatchFace::isEnabled)
                .collect(Collectors.toList());
    }
}










