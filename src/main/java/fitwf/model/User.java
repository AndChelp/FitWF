package fitwf.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    //    @ValidPassword
    @Column(name = "password")
    private String password ="test";

    @Column(name = "enable")
    private boolean enable = true;

    @Column(name = "admin_role")
    private boolean adminRole = false;

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
