package fitwf.repository;

import fitwf.entity.User;
import fitwf.entity.WatchFace;
import fitwf.security.jwt.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE id >= :fromId AND id <= :endId", nativeQuery = true)
    List<User> getList(@Param("fromId") int fromId, @Param("endId") int endId);

    @Query(value = "SELECT last_value FROM users_id_seq;", nativeQuery = true)
    int getLastId();

    @Transactional
    @Modifying
    @Query(value = "CALL UPDATE_USER_PASSWORD(:id_user, :new_password);", nativeQuery = true)
    void updatePassword(@Param("id_user") int userID, @Param("new_password") String password);

    @Transactional
    @Modifying
    @Query(value = "CALL BLOCK_USER(:id_user);", nativeQuery = true)
    void block(@Param("id_user") int userID);

    @Transactional
    @Modifying
    @Query(value = "CALL UNBLOCK_USER(:id_user);", nativeQuery = true)
    void unblock(@Param("id_user") int userID);

}
