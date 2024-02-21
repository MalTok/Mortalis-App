package pl.mt.mortalis.necrology.dto;

import lombok.Data;
import pl.mt.mortalis.UploadedFile;
import pl.mt.mortalis.condolences.dto.CondolencesDisplayDto;

import java.time.LocalDate;
import java.util.List;

@Data
public class NecrologyDisplayDto {
    private String name;
    private String ageString;
    private LocalDate deathDate;
    private String placeOfOrigin;
    private String placeOfFuneral;
    private String genderVerb;
    private UploadedFile file;
    private String title;
    private String kinship;
    private Boolean addCrossAndLate;
    private String funeralDetails;
    private String additionalInfo;
    private Integer candlesAmount;
    private String necrologyIdentifier;
    private List<CondolencesDisplayDto> condolences;
}
