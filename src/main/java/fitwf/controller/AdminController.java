package fitwf.controller;

import fitwf.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    /*
    /api/admin/
    blockUser(int userID);
    unblockUser(int userID);
    deleteAllUserWF(int userID);
    deleteAllUserLikes(int userID);
    */
    @GetMapping("/test")
    public String test() {
        System.out.println(((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
        return "test";
    }
}
