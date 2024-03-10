package pl.mt.mortalis.necrology.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.Gender;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.kinship.KinshipService;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.dto.NecrologyFormDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NecrologyFormDtoMapper {
    private final KinshipService kinshipService;

    public NecrologyFormDtoMapper(KinshipService kinshipService) {
        this.kinshipService = kinshipService;
    }

    public Necrology mapToEntity(NecrologyFormDto necrologyFormDto) throws IOException {
        Necrology necrology = new Necrology();
        necrology.setName(necrologyFormDto.getName());
        necrology.setBirthDate(necrologyFormDto.getBirthDate());
        necrology.setDeathDate(necrologyFormDto.getDeathDate());
        necrology.setPlaceOfOrigin(necrologyFormDto.getPlaceOfOrigin());
        necrology.setPlaceOfFuneral(necrologyFormDto.getPlaceOfFuneral());
        Optional<Gender> genderByName = Gender.findGenderByName(necrologyFormDto.getGender());
        genderByName.ifPresent(necrology::setGender);
        necrology.setPictureBytes(necrologyFormDto.getPictureFile().getBytes());
        necrology.setTitle(necrologyFormDto.getTitle());
        List<Kinship> kinships = kinshipService.findAllByNameIn(necrologyFormDto.getKinship());
        necrology.setKinship(kinships);
        necrology.setAddCrossAndLate(necrologyFormDto.getAddCrossAndLate());
        if (necrologyFormDto.getRemoveAfter14Days()) {
            necrology.setRemovingDate(getRemovingDate());
        }
        necrology.setFuneralDetails(necrologyFormDto.getFuneralDetails());
        necrology.setAdditionalInfo(necrologyFormDto.getAdditionalInfo());
        necrology.setAcceptedTerms(necrologyFormDto.getAcceptedTerms());
        necrology.setFromEmail(necrologyFormDto.getFromEmail());
        return necrology;
    }

    private LocalDateTime getRemovingDate() {
        return LocalDateTime
                .now()
                .plusDays(14)
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }
}
