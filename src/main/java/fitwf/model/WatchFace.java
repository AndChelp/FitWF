package fitwf.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "watchface")
public class WatchFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @Max(50)
    @Column(name = "preview_uri")
    private String preview_uri;

    @NotNull
    @Max(50)
    @Column(name = "file_uri")
    private String file_uri;

    @NotNull
    @Column(name = "downloads")
    private int downloads;

    @NotNull
    @Column(name = "likes")
    private int likes;

    @NotNull
    @Max(200)
    @Column(name = "features")
    private String features;
}
