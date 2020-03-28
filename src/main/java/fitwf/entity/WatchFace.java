package fitwf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "watchfaces")
public class WatchFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @NotNull
    @Column(name = "preview_uri", updatable = false)
    private String preview_uri;

    @NotNull
    @Column(name = "file_uri", updatable = false)
    private String file_uri;

    @NotNull
    @Column(name = "downloads", updatable = false)
    private int downloads;

    @NotNull
    @Column(name = "features", updatable = false)
    private String features;

    @NotNull
    @Column(name = "enable", updatable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedWatchFaces")
    private List<User> userLikes;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoritedWatchFaces")
    private List<User> userFavorites;

}
