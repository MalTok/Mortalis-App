package pl.mt.mortalis.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NecrologyModerationEditDto {
    private Long id;

    @Size(min = 2, max = 77)
    @NotBlank
    private String name;

    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private String placeOfOrigin;

    private String placeOfFuneral;

    @NotNull
    private String gender;

    private String pictureBase64;

    private MultipartFile pictureFile;

    private String title;

    private List<String> kinship;

    private Boolean addCrossAndLate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime removeDate;

    private String funeralDetails;

    private String additionalInfo;
    
    private String fromEmail;
}
