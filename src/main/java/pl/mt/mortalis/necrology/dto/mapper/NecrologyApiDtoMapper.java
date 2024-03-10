package pl.mt.mortalis.necrology.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.candle.Candle;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.dto.NecrologyApiDto;

@Service
public class NecrologyApiDtoMapper {
    public NecrologyApiDto mapEntityToDto(Necrology necrology) {
        NecrologyApiDto necrologyApiDto = new NecrologyApiDto();
        necrologyApiDto.setName(necrology.getName());
        necrologyApiDto.setBirthDate(necrology.getBirthDate());
        necrologyApiDto.setDeathDate(necrology.getDeathDate());
        necrologyApiDto.setPlaceOfOrigin(necrology.getPlaceOfOrigin());
        necrologyApiDto.setPlaceOfFuneral(necrology.getPlaceOfFuneral());
        necrologyApiDto.setGender(necrology.getGender().getPlDisplayName());
        necrologyApiDto.setTitle(necrology.getTitle());
        necrologyApiDto.setFuneralDetails(necrology.getFuneralDetails());
        necrologyApiDto.setAdditionalInfo(necrology.getAdditionalInfo());
        necrologyApiDto.setActiveCandles(necrology.getCandles()
                .stream()
                .filter(Candle::isActivated)
                .mapToInt(e -> 1)
                .reduce(0, Integer::sum));
        return necrologyApiDto;
    }
}
