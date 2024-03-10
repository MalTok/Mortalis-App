package pl.mt.mortalis.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CondolencesModerationDto {
    private Long id;

    private String fromName;

    private String message;
}
