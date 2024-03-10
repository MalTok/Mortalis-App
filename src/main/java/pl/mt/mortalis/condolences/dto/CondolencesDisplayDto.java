package pl.mt.mortalis.condolences.dto;

import lombok.Data;

@Data
public class CondolencesDisplayDto {
    private String necrologyIdentifier;

    private String fromName;

    private String message;
}
