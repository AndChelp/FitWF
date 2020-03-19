package fitwf.controller;

import fitwf.model.User;
import fitwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    /*@Autowired
    public UserService userService;

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable int id) {
        return userService.getUserByID(id);
    }*/
/*
/api/public/
register(RegisterData rd);
login();
recoverPassword();
getWFs(int lastID); //returns 50 wfs
getWFsByFilter(Filter filter);
getWFById(int wfID);*/
}
