package fitwf.service;

import fitwf.dto.WatchFaceDTO;
import fitwf.exception.WatchFaceNotFoundException;
import fitwf.model.WatchFace;
import fitwf.repository.WatchFaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public WatchFaceDTO getWatchFaceByID(int id) {
        WatchFace watchFace = watchFaceRepository.findById(id).orElse(null);
        if (watchFace == null)
            throw new WatchFaceNotFoundException("WatchFace with id=" + id + " not found!");
        return new WatchFaceDTO(watchFace);
    }

    public List<WatchFaceDTO> getFiftyWatchFaces(int fromId) {
        System.out.println("getFiftyWatchFaces");
        List<WatchFace> watchFaceList = watchFaceRepository.getFirst3ByIdGreaterThanEqual(fromId);
        if (watchFaceList.isEmpty())
            throw new WatchFaceNotFoundException("WatchFaces with id=" + fromId + " or higher not found!");
        //ToDO: Если циферблаты есть, но не попадают в диапазон ID - ?
        List<WatchFaceDTO> watchFaceDTOList = new ArrayList<>();
        for (WatchFace watchFace : watchFaceList) {
            watchFaceDTOList.add(new WatchFaceDTO(watchFace));
        }
        return watchFaceDTOList;
    }
}










