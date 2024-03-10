package pl.mt.mortalis.condolences.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.condolences.Condolences;
import pl.mt.mortalis.condolences.dto.CondolencesDisplayDto;

@Service
public class CondolencesDisplayDtoMapper {

    public CondolencesDisplayDto mapEntityToDisplayDto(Condolences condolences) {
        CondolencesDisplayDto condolencesDisplayDto = new CondolencesDisplayDto();
        condolencesDisplayDto.setFromName(condolences.getFromName());
        condolencesDisplayDto.setMessage(condolences.getMessage());
        return condolencesDisplayDto;
    }
}
