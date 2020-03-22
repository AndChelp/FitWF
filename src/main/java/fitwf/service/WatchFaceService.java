package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.Role;
import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.exception.PermissionDeniedException;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.repository.RoleRepository;
import fitwf.repository.WatchFaceRepository;
import fitwf.security.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchFaceService {
    private final WatchFaceRepository watchFaceRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public WatchFaceService(WatchFaceRepository watchFaceRepository, RoleRepository roleRepository) {
        this.watchFaceRepository = watchFaceRepository;
        this.roleRepository = roleRepository;
    }

    public void addNewWF(WatchFace watchFace) {
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
            throw new WatchFaceNotFoundException("WatchFace with id=" + id + " was already deleted");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN);
        boolean isOwner = watchFace.getUser().getId() == user.getId();
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getId() == roleAdmin.getId());
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
}










