package fitwf.repository;

import fitwf.entity.User;
import fitwf.security.jwt.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "CALL UPDATE_USER_PASSWORD(:id_user, :password);", nativeQuery = true)
    void updatePassword(@Param("id_user") int userID, @Param("password") String password);
}
