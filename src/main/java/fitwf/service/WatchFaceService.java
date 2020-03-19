package fitwf.service;

import fitwf.model.WatchFace;
import fitwf.repository.WatchFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public WatchFace getWatchFaceByID(int id) {
        return watchFaceRepository.findById(id).orElse(null);
    }
    
    public List<WatchFace> getFiftyWatchFaces(int fromId){
        return null;
    }
}
