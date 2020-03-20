package fitwf.controller;

import fitwf.dto.RegisterDTO;
import fitwf.dto.WatchFaceDTO;
import fitwf.response.Response;
import fitwf.service.UserService;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private WatchFaceService watchFaceService;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegisterDTO registerDto) {
        userService.registerNewUser(registerDto);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User successfully registered!")
                .build());
    }

    @GetMapping("/watchface")
    public ResponseEntity<Response> getWFbyID(@RequestParam int id) {
        WatchFaceDTO watchFaceDTO = watchFaceService.getWatchFaceByID(id);
        return ResponseEntity.ok(Response.builder().statusMsg("WatchFace found").watchFace(watchFaceDTO).build());
    }

    @GetMapping("/watchfaces")
    public ResponseEntity<Response> getFiftyWFs(@RequestParam int fromId) {
        List<WatchFaceDTO> watchFaceDtoList = watchFaceService.getFiftyWatchFaces(fromId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("Found " + watchFaceDtoList.size() + " WatchFaces")
                .itemCount(watchFaceDtoList.size())
                .watchFaceList(watchFaceDtoList)
                .build());
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
