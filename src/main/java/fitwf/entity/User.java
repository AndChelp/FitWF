package fitwf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "password", updatable = false)
    private String password;

    @Column(name = "enable", updatable = false)
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<WatchFace> watchFace;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "liked_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private List<WatchFace> likedWatchFaces;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private List<WatchFace> favoritedWatchFaces;
}
