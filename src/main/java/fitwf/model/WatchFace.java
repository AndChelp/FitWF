package fitwf.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "watchface")
public class WatchFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedWatchFaces")
    private Set<User> userLikes;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteWatchFaces")
    private Set<User> userFavorites;

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
    @Max(200)
    @Column(name = "features")
    private String features;
}
