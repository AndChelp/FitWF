package fitwf.controller;

import fitwf.dto.WatchFaceDTO;
import fitwf.response.Response;
import fitwf.security.jwt.JwtUser;
import fitwf.service.UserService;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


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

    @PostMapping("/watchfaces/upload")
    public ResponseEntity<Response> uploadNewWF(@RequestParam("bin") MultipartFile watchFaceBin,
                                                @RequestParam("gif") MultipartFile watchFaceGif) throws IOException, NoSuchAlgorithmException {
        watchFaceService.addNewWatchFace(watchFaceBin, watchFaceGif);
        return ResponseEntity.ok(Response.builder()
                .build());
    }

    @GetMapping("/watchfaces/liked")
    public ResponseEntity<Response> getLikedWFs(@RequestParam int offsetId) {
        List<WatchFaceDTO> watchFaceSet = watchFaceService.getFiftyLikedWatchFaces(offsetId);

        return ResponseEntity.ok(Response.builder()
                .itemCount(watchFaceSet.size())
                .watchFaceList(watchFaceSet)
                .build());
    }

    @GetMapping("/watchfaces/favorited")
    public ResponseEntity<Response> getFavoritedWFs(@RequestParam int offsetId) {
        List<WatchFaceDTO> watchFaceSet = watchFaceService.getFiftyFavoritedWatchFaces(offsetId);
        return ResponseEntity.ok(Response.builder()
                .itemCount(watchFaceSet.size())
                .watchFaceList(watchFaceSet)
                .build());
    }

    @PostMapping("/watchfaces/like")
    public ResponseEntity<Response> like(@RequestParam int wfId) {
        watchFaceService.like(wfId);
        return ResponseEntity.ok(Response.builder()
                .build());
    }

    @PostMapping("/watchfaces/favorite")
    public ResponseEntity<Response> favorite(@RequestParam int wfId) {
        watchFaceService.favorite(wfId);
        return ResponseEntity.ok(Response.builder()
                .build());
    }


}


/*
/api/user/etDownloads();
        this.likes = watchFace.getUserLikes(
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

