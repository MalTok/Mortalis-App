package pl.mt.mortalis.necrology.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class NecrologyPreviewDto {
    private String name;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate deathDate;
    private String placeOfFuneral;
    private String title;
    private Boolean addCrossAndLate;
    private String necrologyIdentifier;
}
