package pl.mt.mortalis.admin.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.admin.dto.NecrologyModerationEditDto;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.necrology.Necrology;

import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class NecrologyModerationEditDtoMapper {
    public NecrologyModerationEditDto mapToEditDto(Necrology necrology) {
        NecrologyModerationEditDto necrologyModerationEditDto = new NecrologyModerationEditDto();
        necrologyModerationEditDto.setId(necrology.getId());
        necrologyModerationEditDto.setName(necrology.getName());
        necrologyModerationEditDto.setBirthDate(necrology.getBirthDate());
        necrologyModerationEditDto.setDeathDate(necrology.getDeathDate());
        necrologyModerationEditDto.setPlaceOfOrigin(necrology.getPlaceOfOrigin());
        necrologyModerationEditDto.setPlaceOfFuneral(necrology.getPlaceOfFuneral());
        necrologyModerationEditDto.setGender(necrology.getGender().name());
        if (necrology.getPictureBytes() != null) {
            necrologyModerationEditDto.setPictureBase64(Base64.getEncoder().encodeToString(necrology.getPictureBytes()));
        }
        necrologyModerationEditDto.setTitle(necrology.getTitle());
        necrologyModerationEditDto.setKinship(necrology.getKinship().stream().map(Kinship::getName).collect(Collectors.toList()));
        necrologyModerationEditDto.setAddCrossAndLate(necrology.getAddCrossAndLate());
        necrologyModerationEditDto.setRemoveDate(necrology.getRemovingDate());
        necrologyModerationEditDto.setFuneralDetails(necrology.getFuneralDetails());
        necrologyModerationEditDto.setAdditionalInfo(necrology.getAdditionalInfo());
        necrologyModerationEditDto.setFromEmail(necrology.getFromEmail());
        return necrologyModerationEditDto;
    }
}
