package pl.mt.mortalis.necrology.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.dto.NecrologyPreviewDto;

@Service
public class NecrologyPreviewDtoMapper {
    public NecrologyPreviewDto mapEntityToPreviewDto(Necrology necrology) {
        NecrologyPreviewDto necrologyPreviewDto = new NecrologyPreviewDto();
        necrologyPreviewDto.setName(necrology.getName());
        necrologyPreviewDto.setTitle(necrology.getTitle());
        necrologyPreviewDto.setBirthDate(necrology.getBirthDate());
        necrologyPreviewDto.setDeathDate(necrology.getDeathDate());
        necrologyPreviewDto.setPlaceOfFuneral(necrology.getPlaceOfFuneral());
        necrologyPreviewDto.setAddCrossAndLate(necrology.getAddCrossAndLate());
        necrologyPreviewDto.setNecrologyIdentifier(necrology.getNecrologyIdentifier());
        return necrologyPreviewDto;
    }
}
