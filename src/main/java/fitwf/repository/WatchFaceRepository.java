package fitwf.repository;

import fitwf.model.WatchFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchFaceRepository extends JpaRepository<WatchFace, Integer> {
    List<WatchFace> getFirst3ByIdGreaterThanEqual(int fromId);
}
