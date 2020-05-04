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
        WatchFace watchFace = new WatchFace();
        watchFaceService.addNewWF(watchFace);
        return ResponseEntity.ok(Response.builder()
                .build());
    }
   /* Bearer_eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1In0.G6U0_4wEMuAnBn1Xu2DxrYjT8ETU-0587qV4vmG1-h9RC8iSGjJmuI36si_BNfzpEltXet6A01-5GCG135uqog
    Bearer_eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1In0.G6U0_4wEMuAnBn1Xu2DxrYjT8ETU-0587qV4vmG1-h9RC8iSGjJmuI36si_BNfzpEltXet6A01-5GCG135uqog
    */

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

