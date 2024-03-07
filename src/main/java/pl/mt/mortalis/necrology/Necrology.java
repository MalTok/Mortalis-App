package pl.mt.mortalis.necrology;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.mt.mortalis.Gender;
import pl.mt.mortalis.candle.Candle;
import pl.mt.mortalis.condolences.Condolences;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.validation.NotBadWords;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Necrology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 77)
    @NotBlank
    @NotBadWords
    private String name;

    @NotNull
    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate deathDate;

    private String placeOfOrigin;

    private String placeOfFuneral;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Lob
    private byte[] pictureBytes;

    private String title;

    @OneToMany
    private List<Kinship> kinship;

    private Boolean addCrossAndLate;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime removingDate;

    private String funeralDetails;

    private String additionalInfo;

    private Boolean acceptedTerms;

    private String necrologyIdentifier;

    @OneToMany(mappedBy = "necrology", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Candle> candles;

    @OneToMany(mappedBy = "necrology", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Condolences> condolences;

    private String code;

    private boolean activated;

    private String fromEmail;
}
