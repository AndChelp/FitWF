package fitwf.service;

import fitwf.model.WatchFace;
import fitwf.repository.WatchFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchFaceService {
    private final WatchFaceRepository watchFaceRepository;

    @Autowired
    public WatchFaceService(WatchFaceRepository watchFaceRepository) {
        this.watchFaceRepository = watchFaceRepository;
    }

    public void addNewWF(WatchFace watchFace) {
        watchFaceRepository.save(watchFace);
    }

    public void deleteByID(int id) {
        watchFaceRepository.deleteById(id);
    }

    public WatchFace findWatchFaceByID(int id) {
        return watchFaceRepository.findById(id).orElse(null);
    }
}
