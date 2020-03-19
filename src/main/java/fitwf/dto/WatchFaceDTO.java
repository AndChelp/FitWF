package fitwf.dto;

import fitwf.model.WatchFace;
import lombok.Data;

@Data
public class WatchFaceDTO {
    private int id;

    private String username;

    private String preview_uri;

    private String file_uri;

    private int downloads;

    private int testLikes;
    private String features;


    public WatchFaceDTO(WatchFace watchFace) {
        this.id = watchFace.getId();
        this.username = watchFace.getUser().getUsername();
        this.preview_uri = watchFace.getPreview_uri();
        this.file_uri = watchFace.getFile_uri();
        this.downloads = watchFace.getDownloads();
        this.testLikes = watchFace.getUserLikes().size();
        this.features = watchFace.getFeatures();
    }
}
