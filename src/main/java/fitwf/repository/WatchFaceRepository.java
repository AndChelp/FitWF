package fitwf.repository;

import fitwf.model.WatchFace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchFaceRepository extends JpaRepository<WatchFace, Integer> {
}
