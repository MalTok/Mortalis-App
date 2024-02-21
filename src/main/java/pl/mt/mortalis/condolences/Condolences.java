package pl.mt.mortalis.condolences;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.mt.mortalis.necrology.Necrology;

@Getter
@Setter
@Entity
public class Condolences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromName;

    private String fromEmail;

    private String message;

    private String code;

    private boolean activated;

    @ManyToOne
    @JoinColumn(name = "necrology_id", nullable = false)
    private Necrology necrology;
}
