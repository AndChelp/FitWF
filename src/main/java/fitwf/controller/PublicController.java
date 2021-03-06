package fitwf.controller;

import fitwf.dto.LoginDTO;
import fitwf.dto.RegisterDTO;
import fitwf.dto.WatchFaceDTO;
import fitwf.log.Level;
import fitwf.log.annotation.Log;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final WatchFaceService watchFaceService;
    private final UserService userService;

    @Autowired
    public PublicController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, WatchFaceService watchFaceService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.watchFaceService = watchFaceService;
        this.userService = userService;
    }

    @Log(Level.INFO)
    @PostMapping("/login")
    public ResponseEntity<Response> authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {
        String jwt = jwtAuthentication(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok(Response.builder()
                .statusMsg("Authentication successfully completed")
                .jwtToken(jwt).build());
    }

    @Log(Level.INFO)
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        //TODO: convert registerDTO to user right here (for purpose of validation)
        userService.registerNewUser(registerDTO);
        String jwt = jwtAuthentication(registerDTO.getUsername(), registerDTO.getPassword());
        return ResponseEntity.ok(Response.builder()
                .statusMsg("User successfully registered")
                .jwtToken(jwt)
                .build());
    }

    @Log(Level.INFO)
    @GetMapping("/watchfaces/{wfId}")
    public ResponseEntity<Response> getWFbyID(@PathVariable int wfId) {
        WatchFaceDTO watchFaceDTO = watchFaceService.getWatchFaceByID(wfId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("WatchFace found")
                .watchFace(watchFaceDTO)
                .build());
    }

    @Log(Level.INFO)
    @GetMapping("/watchfaces")
    public ResponseEntity<Response> getFiftyWFs(@RequestParam int offsetId) {
        List<WatchFaceDTO> watchFaceDtoList = watchFaceService.getFiftyWatchFaces(offsetId);
        return ResponseEntity.ok(Response.builder()
                .statusMsg("Found " + watchFaceDtoList.size() + " WatchFaces")
                .itemCount(watchFaceDtoList.size())
                .watchFaceList(watchFaceDtoList)
                .build());
    }

    private String jwtAuthentication(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateJwtToken(authentication);
    }
/*
/api/public/
+register(RegisterData rd);
+login();
? recoverPassword();
+ getWFs(int lastID); //returns 50 wfs
? getWFsByFilter(Filter filter);
+ getWFById(int wfID);
*/
}
