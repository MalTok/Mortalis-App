package pl.mt.mortalis.condolences;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CondolencesRepository extends JpaRepository<Condolences, Long> {

    Optional<Condolences> findByCode(String code);

    List<Condolences> findAllByActivatedTrueOrderByIdDesc();

    void deleteAllByActivatedIsFalse();
}
