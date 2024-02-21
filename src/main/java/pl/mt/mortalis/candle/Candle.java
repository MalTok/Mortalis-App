package pl.mt.mortalis.candle;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.mt.mortalis.necrology.Necrology;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime expirationDateTime;

    private String code;

    private boolean activated;

    @ManyToOne
    @JoinColumn(name = "necrology_id", nullable = false)
    private Necrology necrology;
}
