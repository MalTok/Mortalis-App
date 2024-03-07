package pl.mt.mortalis.kinship;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KinshipService {
    private final KinshipRepository kinshipRepository;

    public KinshipService(KinshipRepository kinshipRepository) {
        this.kinshipRepository = kinshipRepository;
    }

    public List<Kinship> findAll() {
        return kinshipRepository.findAll();
    }

    public List<Kinship> findAllByNameIn(List<String> kinshipList) {
        return kinshipRepository.findAllByNameIn(kinshipList);
    }
}
