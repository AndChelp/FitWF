package fitwf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @ValidUsername
    @Column(name = "username", updatable = false)
    private String username;

    @Email
    @Column(name = "email", updatable = false)
    private String email;

    //    @ValidPassword
    @Column(name = "password", updatable = false)
    private String password;

    @Column(name = "enable", updatable = false)
    private boolean enable = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<WatchFace> watchFace;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns= @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "liked_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private Set<WatchFace> likedWatchFaces;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private Set<WatchFace> favoriteWatchFaces;
}
