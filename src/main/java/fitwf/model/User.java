package fitwf.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Min(3)
    @Max(30)
//    @ValidUsername
    @Column(name = "username")
    private String username;

    @NotNull
    @Max(300)
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Min(6)
    @Max(60)
//    @ValidPassword
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "status")
    private short status;

    @NotNull
    @Max(5)
    @Column(name = "role")
    private String role;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private WatchFace watchFace;
}
