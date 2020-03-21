package fitwf.repository;

import fitwf.model.Role;
import fitwf.security.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleName roleName);
}
