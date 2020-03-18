package fitwf.controller;

import fitwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    public UserService userService;

/*  
User capabilities:
    login();
    register(RegisterData rd);
    changePassword(String oldPassword, String newPassword);
    //regainPassword();
    getWFs(int lastID); //returns 50 wfs
    getLikedWFs(int lastID); //returns 50 wfs
    getFavoritedWFs(int lastID); //50 WFs
    
*/
}
