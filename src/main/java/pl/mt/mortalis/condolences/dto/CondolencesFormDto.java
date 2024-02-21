package pl.mt.mortalis.condolences.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CondolencesFormDto {
    private Long necrologyId;
    private String fromName;
    @Email
    @NotBlank
    private String fromEmail;
    private String message;
}
