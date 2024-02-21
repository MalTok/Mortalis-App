package pl.mt.mortalis.candle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CandleFormDto {
    private Long necrologyId;
    @Email
    @NotBlank
    private String email;
}
