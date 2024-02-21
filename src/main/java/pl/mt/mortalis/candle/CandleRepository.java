package pl.mt.mortalis.candle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CandleRepository extends JpaRepository<Candle, Long> {

    Optional<Candle> findByCode(String code);

    void deleteAllByExpirationDateTimeBefore(LocalDateTime date);

    void deleteAllByActivatedIsFalse();
}
