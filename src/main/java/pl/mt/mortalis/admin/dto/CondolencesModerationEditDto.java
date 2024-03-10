package pl.mt.mortalis.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CondolencesModerationEditDto {
    private Long id;

    private String fromName;

    private String fromEmail;

    private String message;
}
