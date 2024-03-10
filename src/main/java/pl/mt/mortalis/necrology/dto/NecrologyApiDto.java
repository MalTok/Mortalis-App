package pl.mt.mortalis.necrology.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NecrologyApiDto {
    private String name;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String placeOfOrigin;

    private String placeOfFuneral;

    private String gender;

    private String title;

    private String funeralDetails;

    private String additionalInfo;

    private Integer activeCandles;
}
