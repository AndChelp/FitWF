package fitwf.controller;

import fitwf.entity.User;
import fitwf.response.Response;
import fitwf.security.jwt.JwtUser;
import fitwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /*
    /api/admin/
    blockUser(int userID);
    unblockUser(int userID);
    deleteAllUserWF(int userID);
    deleteAllUserLikes(int userID);
    deleteWF(int wfID);
    */
    @GetMapping("/test")
    public String test() {
        System.out.println(((JwtUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
        return "test";
    }

    @PutMapping("/users/block")
    public ResponseEntity<Response> blockUser(@RequestParam int id) {
        userService.blockUser(id);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User blocked")
                .build());
    }

    @PutMapping("/users/unblock")
    public ResponseEntity<Response> unblockUser(@RequestParam int id) {
        userService.unblockUser(id);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User unblocked")
                .build());
    }
}
