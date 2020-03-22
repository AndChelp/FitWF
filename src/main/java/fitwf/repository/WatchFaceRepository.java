package fitwf.repository;

import fitwf.entity.WatchFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchFaceRepository extends JpaRepository<WatchFace, Integer> {
    //select * from wathcfaces where id<{} and enable = true order by desc limit 3
    List<WatchFace> getFirst3ByIdLessThanEqualAndEnabledTrueOrderByIdDesc(int fromId);

    @Query(value = "SELECT last_value FROM watchfaces_id_seq;", nativeQuery = true)
    int getLastId();
}
