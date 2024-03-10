package pl.mt.mortalis.admin.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.admin.dto.CondolencesModerationDto;
import pl.mt.mortalis.condolences.Condolences;

@Service
public class CondolencesModerationDtoMapper {
    public CondolencesModerationDto mapEntityToModerationDto(Condolences condolences) {
        return new CondolencesModerationDto(
                condolences.getId(),
                condolences.getFromName(),
                condolences.getMessage()
        );
    }
}
