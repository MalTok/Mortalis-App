package pl.mt.mortalis.condolences.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pl.mt.mortalis.validation.NotBadWords;

@Data
public class CondolencesFormDto {
    private Long necrologyId;

    private String fromName;

    @Email
    @NotBlank
    private String fromEmail;

    @NotBlank
    @NotBadWords(message = "Wulgaryzmy nie są dozwolone")
    private String message;
}
