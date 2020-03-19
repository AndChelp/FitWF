package fitwf.controller;

import fitwf.dto.WatchFaceDTO;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private WatchFaceService watchFaceService;
    @Autowired
    private UserService userService;

    @GetMaping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDto){
        userService.registerNewUser(registerDto);
        return ResponseEntity.ok(new ...);
    }

    @GetMapping("/watchface")
    public ResponseEntity getWFbyID(@RequestParam int id) {
        return new WatchFaceDTO(watchFaceService.getWatchFaceByID(id));
    }
    
    @GetMapping("/watchface")
    public ResponseEntity getFiftyWFs(@RequestParam int fromId) {
        List<WatchFaceDTO> watchFaceDtoList = watchFaceService.getFiftyWatchFaces(fromId);
        return null;
    }
    
   // @GetMapping("/watchface")
/*
/api/public/
+register(RegisterData rd);
login();
? recoverPassword();
+ getWFs(int lastID); //returns 50 wfs
? getWFsByFilter(Filter filter);
+ getWFById(int wfID);
*/
}
