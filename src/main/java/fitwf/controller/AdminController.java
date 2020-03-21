package fitwf.controller;

import fitwf.model.UserPrinciple;
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
        System.out.println(((UserPrinciple) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername());
        return "test";
    }
}
