package fitwf.repository;

import fitwf.entity.WatchFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WatchFaceRepository extends JpaRepository<WatchFace, Integer> {
    //select * from wathcfaces where id<=fromId and enable = true order by id desc limit 3
    List<WatchFace> /*TODO:replace 3 with 50*/getFirst3ByIdLessThanEqualAndEnabledTrueOrderByIdDesc(int fromId);

    @Query(value = "SELECT last_value FROM watchfaces_id_seq;", nativeQuery = true)
    int getLastId();

    @Transactional
    @Modifying
    @Query(value = "CALL DISABLE_USER_WATCHFACES(:userId)", nativeQuery = true)
    void deleteByUser(@Param("userId") Integer userId);

    @Override
    @Transactional
    @Modifying
    @Query(value = "CALL DISABLE_WATCHFACE(:wfId)", nativeQuery = true)
    void deleteById(@Param("wfId") Integer wfId);

    @Transactional
    @Modifying
    @Query(value = "CALL LIKE_WATCHFACE(:userId, :wfId)", nativeQuery = true)
    void like(@Param("userId") Integer userId, @Param("wfId") Integer wfId);

    @Transactional
    @Modifying
    @Query(value = "SELECT CHECK_LIKE_WATCHFACE(:userId, :wfId)", nativeQuery = true)
    boolean checkLike(@Param("userId") Integer userId, @Param("wfId") Integer wfId);

    @Transactional
    @Modifying
    @Query(value = "CALL FAVORITE_WATCHFACE(:userId, :wfId)", nativeQuery = true)
    void favorite(@Param("userId") Integer userId, @Param("wfId") Integer wfId);

    @Transactional
    @Modifying
    @Query(value = "SELECT CHECK_FAVORITE_WATCHFACE(:userId, :wfId)", nativeQuery = true)
    boolean checkFavorite(@Param("userId") Integer userId, @Param("wfId") Integer wfId);


}
