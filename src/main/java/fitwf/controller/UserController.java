package fitwf.controller;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.response.Response;
import fitwf.security.jwt.JwtUser;
import fitwf.service.UserService;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final WatchFaceService watchFaceService;
    private final UserService userService;

    @Autowired
    public UserController(WatchFaceService watchFaceService, UserService userService) {
        this.watchFaceService = watchFaceService;
        this.userService = userService;
    }

    @PutMapping("/password/change")
    public ResponseEntity<Response> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("Password successfully changed")
                .build());
    }

    @PostMapping("/watchfaces/add")
    public ResponseEntity<Response> addNewWF( /*@RequestParam MultipartFile watchFaceBin*/) {
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
        WatchFace watchFace = new WatchFace();
        watchFace.setFeatures("awesome features");
        watchFace.setFile_uri("file");
        watchFace.setPreview_uri("preview");
        watchFace.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        watchFaceService.addNewWF(watchFace);
        return ResponseEntity.ok(Response.builder()
                .build());
    }

    @DeleteMapping("/watchfaces/delete")
    public ResponseEntity<Response> deleteWF(@RequestParam int id) {
        watchFaceService.deleteByID(id);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("WatchFace with ID=" + id + " successfully deleted")
                .build());
    }

    @GetMapping("/likes/liked")
    public ResponseEntity<Response> getLikedWFs(@RequestParam int offset) {
        List<WatchFace> watchFaceSet = watchFaceService.getFiftyLikedWatchFaces(offset);
        return ResponseEntity.ok(Response.builder()
                .itemCount(watchFaceSet.size())
                .watchFaceList(watchFaceSet.stream().map(WatchFaceDTO::new).collect(Collectors.toList()))
                .build());
    }

    @GetMapping("/favorite/favorited")
    public ResponseEntity<Response> getFavoritedWFs(@RequestParam int offset) {
        List<WatchFace> watchFaceSet = watchFaceService.getFiftyFavoritedWatchFaces(offset);
        return ResponseEntity.ok(Response.builder()
                .itemCount(watchFaceSet.size())
                .watchFaceList(watchFaceSet.stream().map(WatchFaceDTO::new).collect(Collectors.toList()))
                .build());
    }
}


/*
/api/user/
+changePassword(String oldPassword, String newPassword);

+getLikedWFs(int lastID); //returns 50 wfs
+getFavoriteWFs(int lastID); //50 WFs

?addWF(WF wf);
+deleteWF(int wfID);

setLike(int wfID);
deleteLike(int wfID);

addToFavorites(int wfID);
deleteFromFavorites(int wfID);
*/

