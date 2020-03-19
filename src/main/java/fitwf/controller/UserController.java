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

/*  User capabilities:

    register
    publicWatchFace
    deleteWatchFace as owner
    changePassword as owner
    setLike
    removeLike
    addToFavorites
    removeFromFavorites*/
}
