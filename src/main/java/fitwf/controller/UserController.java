package fitwf.controller;

import fitwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }
/*  
/api/user/
changePassword(String oldPassword, String newPassword);
getLikedWFs(int lastID); //returns 50 wfs
getFavoriteWFs(int lastID); //50 WFs
addWF(WF wf);
deleteWF(int wfID);
setLike(int wfID);
deleteLike(int wfID);
addToFavorites(int wfID);
deleteFromFavorites(int wfID);
*/
}
