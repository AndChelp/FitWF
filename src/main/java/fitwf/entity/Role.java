package fitwf.entity;

import fitwf.security.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", updatable = false)
    private RoleName name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<User> users;
}
