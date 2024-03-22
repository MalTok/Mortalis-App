package pl.mt.mortalis.necrology.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pl.mt.mortalis.validation.NotBadWords;

import java.time.LocalDate;
import java.util.List;

@Data
public class NecrologyFormDto {
    @Size(min = 2, max = 77)
    @NotBlank
    @NotBadWords(message = "Nie można używać wulgaryzmów")
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
    private String gender;

    private MultipartFile pictureFile;

    private String title;

    private List<String> kinship;

    private Boolean addCrossAndLate;

    private Boolean removeAfter14Days;

    private String funeralDetails;

    private String additionalInfo;

    @AssertTrue
    private Boolean acceptedTerms;

    @NotBlank
    @Email
    private String fromEmail;
}
