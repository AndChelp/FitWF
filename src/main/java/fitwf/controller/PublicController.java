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

    @GetMapping("/watchface")
    public WatchFaceDTO findWFbyID(@RequestParam int id) {
        return new WatchFaceDTO(watchFaceService.findWatchFaceByID(id));
    }
   // @GetMapping("/watchface")
/*
/api/public/
register(RegisterData rd);
login();
recoverPassword();
getWFs(int lastID); //returns 50 wfs
getWFsByFilter(Filter filter);
+ getWFById(int wfID);
*/
}
