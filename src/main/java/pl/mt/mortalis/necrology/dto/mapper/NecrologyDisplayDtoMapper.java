package pl.mt.mortalis.necrology.dto.mapper;

import org.springframework.stereotype.Service;
import pl.mt.mortalis.condolences.dto.CondolencesDisplayDto;
import pl.mt.mortalis.condolences.dto.mapper.CondolencesDisplayDtoMapper;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.dto.NecrologyDisplayDto;

import java.time.Period;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NecrologyDisplayDtoMapper {
    private final CondolencesDisplayDtoMapper condolencesDisplayDtoMapper;

    public NecrologyDisplayDtoMapper(CondolencesDisplayDtoMapper condolencesDisplayDtoMapper) {
        this.condolencesDisplayDtoMapper = condolencesDisplayDtoMapper;
    }

    public NecrologyDisplayDto maptoDisplayDto(Necrology necrology) {
        NecrologyDisplayDto necrologyDisplayDto = new NecrologyDisplayDto();
        necrologyDisplayDto.setName(necrology.getName());
        necrologyDisplayDto.setAgeString(getAgeString(necrology));
        necrologyDisplayDto.setDeathDate(necrology.getDeathDate());
        necrologyDisplayDto.setPlaceOfOrigin(necrology.getPlaceOfOrigin());
        necrologyDisplayDto.setPlaceOfFuneral(necrology.getPlaceOfFuneral());
        necrologyDisplayDto.setGenderVerb(getGenderVerb(necrology));
        if (necrology.getPictureBytes() != null) {
            necrologyDisplayDto.setPictureBase64(Base64.getEncoder().encodeToString(necrology.getPictureBytes()));
        }
        necrologyDisplayDto.setTitle(necrology.getTitle());
        necrologyDisplayDto.setKinship(getKinshipString(necrology));
        necrologyDisplayDto.setAddCrossAndLate(necrology.getAddCrossAndLate());
        necrologyDisplayDto.setFuneralDetails(necrology.getFuneralDetails());
        necrologyDisplayDto.setAdditionalInfo(necrology.getAdditionalInfo());
        necrologyDisplayDto.setCandlesAmount(necrology.getCandles().size());
        necrologyDisplayDto.setNecrologyIdentifier(necrology.getNecrologyIdentifier());
        necrologyDisplayDto.setCondolences(getCondolencesList(necrology));
        return necrologyDisplayDto;
    }

    private List<CondolencesDisplayDto> getCondolencesList(Necrology necrology) {
        return necrology.getCondolences()
                .stream()
                .map(condolencesDisplayDtoMapper::mapToDisplayDto)
                .collect(Collectors.toList());
    }

    private String getKinshipString(Necrology necrology) {
        return necrology.getKinship()
                .stream()
                .map(Kinship::getPlName)
                .collect(Collectors.joining(", "));
    }

    private String getGenderVerb(Necrology necrology) {
        if (necrology.getGender().name().equals("MALE")) {
            return "odszedł Najdroższy";
        }
        return "odeszła Najdroższa";
    }

    private String getAgeString(Necrology necrology) {
        Period between = Period.between(
                necrology.getBirthDate(),
                necrology.getDeathDate()
        );
        int years = between.getYears();
        int months = between.getMonths();
        int days = between.getDays();

        if (years > 1) {
            return years + " lat";
        } else if (years == 1) {
            return "1 roku";
        } else if (years == 0 && months > 1) {
            return months + " miesięcy";
        } else if (years == 0 && months == 1) {
            return "1 miesiąca";
        } else if (years == 0 && months == 0 && days > 7) {
            return days / 7 + " tygodni";
        } else if (years == 0 && months == 0 && days == 7) {
            return "1 tygodnia";
        } else if (years == 0 && months == 0 && days == 1) {
            return "1 dnia";
        } else if (years == 0 && months == 0) {
            return days + " dni";
        } else {
            throw new InputMismatchException("Incorrect dates input");
        }
    }
}
