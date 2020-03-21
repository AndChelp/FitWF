package fitwf.controller;

import fitwf.dto.LoginDTO;
import fitwf.dto.RegisterDTO;
import fitwf.dto.WatchFaceDTO;
import fitwf.response.Response;
import fitwf.security.jwt.JwtProvider;
import fitwf.service.UserService;
import fitwf.service.WatchFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private WatchFaceService watchFaceService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response> authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("Authentication completed!")
                .jwtToken(jwt).build());
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        //TODO: convert registerDTO to user right here (for purpose of validation)
        userService.registerNewUser(registerDTO);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerDTO.getUsername(),
                        registerDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User successfully registered!")
                .jwtToken(jwt)
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
