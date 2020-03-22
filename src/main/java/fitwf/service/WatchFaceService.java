package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.entity.WatchFace;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.repository.WatchFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public WatchFaceDTO getWatchFaceByID(int id) {
        WatchFace watchFace = watchFaceRepository.findById(id).orElse(null);
        if (watchFace == null)
            throw new WatchFaceNotFoundException("WatchFace with id=" + id + " not found!");
        return new WatchFaceDTO(watchFace);
    }

    public List<WatchFaceDTO> getFiftyWatchFaces(int offsetId) {
        int lastId = watchFaceRepository.getLastId();
        if (lastId == offsetId)
            throw new WatchFaceNotFoundException("There no more WatchFaces");

        return watchFaceRepository
                .getFirst3ByIdLessThanEqualAndEnabledTrueOrderByIdDesc(lastId - offsetId)
                .stream()
                .map(WatchFaceDTO::new)
                .collect(Collectors.toList());
    }
}










