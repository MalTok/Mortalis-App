package pl.mt.mortalis.necrology;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NecrologyRepository extends JpaRepository<Necrology, Long> {

    Optional<Necrology> findByNecrologyIdentifierIs(String identifier);

    Optional<Necrology> findByCode(String code);

    List<Necrology> findAllByActivatedIsTrue();

    Page<Necrology> findAllByActivatedIsTrue(Pageable page);

    @Query("SELECT n FROM Necrology n " +
            "WHERE " +
            "n.activated = true AND " +
            "LOWER(n.name) LIKE %?1% OR " +
            "LOWER(n.placeOfFuneral) LIKE %?1% OR " +
            "LOWER(n.placeOfOrigin) LIKE %?1%")
    List<Necrology> findByWord(String word);

    @Query("SELECT n FROM Necrology n WHERE n.activated = true " +
            "AND LOWER(n.name) LIKE %?1% " +
            "AND LOWER(n.placeOfFuneral) LIKE %?2%")
    List<Necrology> findAllByActivatedIsTrueAndNameAndPlace(String name, String place);

    List<Necrology> findAllByActivatedIsTrueAndPlaceOfFuneralContainsIgnoreCase(String place);

    List<Necrology> findAllByActivatedIsTrueAndNameContainsIgnoreCase(String name);

    @Query(value = "SELECT * FROM necrology WHERE activated = true ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Necrology> findAllByActivatedIsTrueOrderByIdDescLimited(Integer last);

    List<Necrology> findAllByOrderByIdDesc();

    void deleteAllByRemovingDateIsBefore(LocalDateTime date);

    void deleteAllByActivatedIsFalse();
}
