package fitwf.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ValidUsername
    @Column(name = "username")
    private String username;

    @Max(300)
    @Email
    @Column(name = "email")
    private String email;

    @ValidPassword
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "status")
    private short status;

    @NotNull
    @Max(5)
    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<WatchFace> watchFace;

    @ManyToMany
    @JoinTable(name = "liked_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private Set<WatchFace> likedWatchFaces;

    @ManyToMany
    @JoinTable(name = "favorite_wf",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_wf", referencedColumnName = "id"))
    private Set<WatchFace> favoriteWatchFaces;
}
