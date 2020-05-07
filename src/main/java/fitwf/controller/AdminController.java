package fitwf.controller;

import fitwf.response.Response;
import fitwf.security.jwt.JwtUser;
import fitwf.service.UserService;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final WatchFaceService watchFaceService;

    @Autowired
    public AdminController(UserService userService, WatchFaceService watchFaceService) {
        this.userService = userService;
        this.watchFaceService = watchFaceService;
    }

    /*
    /api/admin/
    +blockUser(int userID);
    +unblockUser(int userID);
    deleteAllUserWF(int userID);
    deleteWF(int wfID);
    */
    @GetMapping("/test")
    public String test() {
        System.out.println(((JwtUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
        return "test";
    }

    @PutMapping("/users/watchfaces/delete/all")
    public ResponseEntity<Response> deleteUserWF(@RequestParam int userId) {
        watchFaceService.deleteByUser(userId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User WatchFaces with ID=" + userId + " successfully deleted")
                .build());
    }

    @PutMapping("/users/watchfaces/delete")
    public ResponseEntity<Response> deleteWF(@RequestParam int wfId) {
        watchFaceService.deleteByID(wfId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("WatchFace with ID=" + wfId + " successfully deleted")
                .build());
    }

    @PutMapping("/users/block")
    public ResponseEntity<Response> blockUser(@RequestParam int userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User blocked")
                .build());
    }

    @PutMapping("/users/unblock")
    public ResponseEntity<Response> unblockUser(@RequestParam int userId) {
        userService.unblockUser(userId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User unblocked")
                .build());
    }
}
