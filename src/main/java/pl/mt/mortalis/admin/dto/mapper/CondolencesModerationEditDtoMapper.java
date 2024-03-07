package pl.mt.mortalis.admin.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.admin.dto.CondolencesModerationEditDto;
import pl.mt.mortalis.condolences.Condolences;

@Service
public class CondolencesModerationEditDtoMapper {
    public CondolencesModerationEditDto mapToEditDto(Condolences condolences) {
        return new CondolencesModerationEditDto(
                condolences.getId(),
                condolences.getFromName(),
                condolences.getFromEmail(),
                condolences.getMessage()
        );
    }
}
