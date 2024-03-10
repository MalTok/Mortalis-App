package pl.mt.mortalis.admin.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.admin.dto.NecrologyModerationDto;
import pl.mt.mortalis.necrology.Necrology;

@Service
public class NecrologyModerationDtoMapper {
    public NecrologyModerationDto mapEntityToModerationDto(Necrology necrology) {
        NecrologyModerationDto necrologyModerationDto = new NecrologyModerationDto();
        necrologyModerationDto.setId(necrology.getId());
        necrologyModerationDto.setPlaceOfOrigin(necrology.getPlaceOfOrigin());
        necrologyModerationDto.setPlaceOfFuneral(necrology.getPlaceOfFuneral());
        necrologyModerationDto.setTitle(necrology.getTitle());
        necrologyModerationDto.setFuneralDetails(necrology.getFuneralDetails());
        necrologyModerationDto.setAdditionalInfo(necrology.getAdditionalInfo());
        return necrologyModerationDto;
    }
}
