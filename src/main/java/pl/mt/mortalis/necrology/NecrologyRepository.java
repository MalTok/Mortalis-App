package pl.mt.mortalis.necrology;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NecrologyRepository extends JpaRepository<Necrology, Long> {
    Optional<Necrology> findByNecrologyIdentifierIs(String identifier);

    Optional<Necrology> findByCode(String code);

    List<Necrology> findAllByActivatedIsTrue();

    //poprawić
    @Query("SELECT DISTINCT n FROM Necrology n " +
            "LEFT JOIN Candle can ON n.id = can.necrology.id AND can.activated = true " +
            "LEFT JOIN Condolences con ON n.id = con.necrology.id AND con.activated = true " +
            "WHERE n.necrologyIdentifier = ?1 AND n.activated = true")
    Optional<Necrology> findActivated(String identifier);

    @Query("SELECT n FROM Necrology n " +
            "WHERE " +
            "n.activated = true AND " +
            "n.name LIKE %?1% OR " +
            "n.placeOfFuneral LIKE %?1% OR " +
            "n.placeOfOrigin LIKE %?1%")
    List<Necrology> findByWord(String word);

    @Query("SELECT n FROM Necrology n WHERE n.activated = true " +
            "AND LOWER(n.name) LIKE %?1% " +
            "AND LOWER(n.placeOfFuneral) LIKE %?2%")
    List<Necrology> findAllByActivatedIsTrueAndNameAndPlace(String name, String place);

    List<Necrology> findAllByActivatedIsTrueAndPlaceOfFuneralContainsIgnoreCase(String place);

    List<Necrology> findAllByActivatedIsTrueAndNameContainsIgnoreCase(String name);

    @Query(value = "SELECT * FROM Necrology WHERE activated = true ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Necrology> findAllByActivatedIsTrueOrderByIdDescLimited(Integer last);

    void deleteAllByRemovingDateIsBefore(LocalDateTime date);

    void deleteAllByActivatedIsFalse();
}
