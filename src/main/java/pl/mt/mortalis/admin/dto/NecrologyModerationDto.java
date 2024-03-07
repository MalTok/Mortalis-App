package pl.mt.mortalis.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NecrologyModerationDto {
    private Long id;

    private String placeOfOrigin;

    private String placeOfFuneral;

    private String title;

    private String funeralDetails;

    private String additionalInfo;
}
