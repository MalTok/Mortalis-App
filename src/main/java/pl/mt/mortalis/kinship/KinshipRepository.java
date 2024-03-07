package pl.mt.mortalis.kinship;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KinshipRepository extends JpaRepository<Kinship, Long> {

    List<Kinship> findAllByNameIn(List<String> list);
}
