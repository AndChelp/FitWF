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

    @GetMapping("/test")
    public String test() {
        System.out.println(((JwtUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
        return "test";
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
        WatchFace watchFace = new WatchFace();
        watchFaceService.addNewWF(watchFace);
        return ResponseEntity.ok(Response.builder()
                .build());
    }
/*
    @DeleteMapping("/watchfaces/delete")
    public ResponseEntity<Response> deleteWF(@RequestParam int wfId) {
        watchFaceService.deleteByID(wfId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("WatchFace with ID=" + wfId + " successfully deleted")
                .build());
    }
*/
    @GetMapping("/watchfaces/liked")
    public ResponseEntity<Response> getLikedWFs(@RequestParam int offsetId) {
        List<WatchFace> watchFaceSet = watchFaceService.getFiftyLikedWatchFaces(offsetId);
        return ResponseEntity.ok(Response.builder()
                .itemCount(watchFaceSet.size())
                .watchFaceList(watchFaceSet.stream().map(WatchFaceDTO::new).collect(Collectors.toList()))
                .build());
    }

    @GetMapping("/watchfaces/avorited")
    public ResponseEntity<Response> getFavoritedWFs(@RequestParam int offsetId) {
        List<WatchFace> watchFaceSet = watchFaceService.getFiftyFavoritedWatchFaces(offsetId);
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

