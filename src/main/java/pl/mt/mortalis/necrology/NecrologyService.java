package pl.mt.mortalis.necrology;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.mt.mortalis.Gender;
import pl.mt.mortalis.admin.dto.NecrologyModerationDto;
import pl.mt.mortalis.admin.dto.NecrologyModerationEditDto;
import pl.mt.mortalis.admin.dto.mapper.NecrologyModerationDtoMapper;
import pl.mt.mortalis.admin.dto.mapper.NecrologyModerationEditDtoMapper;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.kinship.KinshipService;
import pl.mt.mortalis.mail.ActivationManager;
import pl.mt.mortalis.mail.MailService;
import pl.mt.mortalis.mail.Message;
import pl.mt.mortalis.necrology.dto.*;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyDisplayDtoMapper;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyFormDtoMapper;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyPreviewDtoMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NecrologyService {
    private static final int LAST_ADDED_AMOUNT = 10;
    private static final int PAGE_SIZE = 8;

    private final NecrologyRepository necrologyRepository;
    private final NecrologyFormDtoMapper necrologyFormDtoMapper;
    private final NecrologyDisplayDtoMapper necrologyDisplayDtoMapper;
    private final NecrologyPreviewDtoMapper necrologyPreviewDtoMapper;
    private final NecrologyModerationDtoMapper necrologyModerationDtoMapper;
    private final NecrologyModerationEditDtoMapper necrologyModerationEditDtoMapper;
    private final MailService mailService;
    private final KinshipService kinshipService;
    private final ActivationManager activationManager;

    public NecrologyService(NecrologyRepository necrologyRepository,
                            NecrologyFormDtoMapper necrologyFormDtoMapper,
                            NecrologyDisplayDtoMapper necrologyDisplayDtoMapper,
                            NecrologyPreviewDtoMapper necrologyPreviewDtoMapper,
                            NecrologyModerationDtoMapper necrologyModerationDtoMapper,
                            NecrologyModerationEditDtoMapper necrologyModerationEditDtoMapper,
                            MailService mailService,
                            KinshipService kinshipService,
                            ActivationManager activationManager) {
        this.necrologyRepository = necrologyRepository;
        this.necrologyFormDtoMapper = necrologyFormDtoMapper;
        this.necrologyDisplayDtoMapper = necrologyDisplayDtoMapper;
        this.necrologyPreviewDtoMapper = necrologyPreviewDtoMapper;
        this.necrologyModerationDtoMapper = necrologyModerationDtoMapper;
        this.necrologyModerationEditDtoMapper = necrologyModerationEditDtoMapper;
        this.mailService = mailService;
        this.kinshipService = kinshipService;
        this.activationManager = activationManager;
    }

    @Transactional
    public void startActivation(NecrologyFormDto necrologyFormDto) {
        try {
            Necrology necrology = save(necrologyFormDto);
            String necrologyIdentifier = createNecrologyIdentifier(necrology);
            necrology.setNecrologyIdentifier(necrologyIdentifier);
            String code = activationManager.createCode();
            necrology.setCode(code);
            necrology.setActivated(false);
            sendActivationEmail(necrologyFormDto, code);
        } catch (IOException e) {
            throw new RuntimeException("File could not be saved");
        }
    }

    @Transactional
    public Necrology save(NecrologyFormDto necrologyFormDto) throws IOException {
        return necrologyRepository.save(necrologyFormDtoMapper
                .mapToEntity(necrologyFormDto));
    }

    private String createNecrologyIdentifier(Necrology necrology) {
        String stripped = StringUtils.stripAccents(necrology.getName());
        return stripped
                .toLowerCase()
                .trim()
                .replace(" ", "-")
                + "-"
                + necrology.getId();
    }

    private void sendActivationEmail(NecrologyFormDto necrologyFormDto, String code) {
        try {
            mailService.sendActivationEmail(
                    new Message(
                            necrologyFormDto.getFromEmail(),
                            "Aktywacja - dodaj nekrolog",
                            activationManager.createNecrologyActivationMessageText(code)
                    )
            );
        } catch (MessagingException e) {
            throw new RuntimeException(ActivationManager.MESSAGING_EXCEPTION_TEXT);
        }
    }

    public NecrologyDisplayDto mapToDisplayDto(Necrology necrology) {
        return necrologyDisplayDtoMapper.mapEntityToDisplayDto(necrology);
    }

    @Transactional
    public String validateCode(String code) {
        Optional<Necrology> optionalNecrology = necrologyRepository.findByCode(code);
        return optionalNecrology
                .map(this::activate)
                .orElseThrow(EntityNotFoundException::new);
    }

    private String activate(Necrology necrology) {
        necrology.setActivated(true);
        return necrology.getNecrologyIdentifier();
    }

    public Optional<Necrology> findByIdentifier(String identifier) {
        return necrologyRepository.findByNecrologyIdentifierIs(identifier);
    }

    public Optional<Necrology> findActivated(String identifier) {
        return necrologyRepository.findByNecrologyIdentifierIs(identifier);
    }

    public Page<NecrologyPreviewDto> findAllPaged(int pageNo) {
        Pageable page = PageRequest.of(pageNo, PAGE_SIZE, Sort.by("id").descending());
        return necrologyRepository.findAllByActivatedIsTrue(page)
                .map(necrologyPreviewDtoMapper::mapEntityToPreviewDto);
    }

    public List<Necrology> findAllByActivatedIsTrue() {
        return necrologyRepository.findAllByActivatedIsTrue();
    }

    public Optional<Necrology> findById(Long id) {
        return necrologyRepository.findById(id);
    }

    public List<NecrologyPreviewDto> search(String word) {
        List<Necrology> necrologies = necrologyRepository.findByWord(word.toLowerCase());
        return necrologies.stream()
                .map(necrologyPreviewDtoMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    public List<NecrologyPreviewDto> findLast10Activated() {
        List<Necrology> necrologies = necrologyRepository.findAllByActivatedIsTrueOrderByIdDescLimited(LAST_ADDED_AMOUNT);
        return necrologies.stream()
                .map(necrologyPreviewDtoMapper::mapEntityToPreviewDto)
                .collect(Collectors.toList());
    }

    public List<Necrology> findAllByNameAndPlaceAndActivated(String name, String place) {
        return necrologyRepository.findAllByActivatedIsTrueAndNameAndPlace(
                name.toLowerCase(),
                place.toLowerCase());
    }

    public List<Necrology> findAllByNameAndActivated(String name) {
        return necrologyRepository.findAllByActivatedIsTrueAndNameContainsIgnoreCase(name.toLowerCase());
    }

    public List<Necrology> findAllByPlaceAndActivated(String place) {
        return necrologyRepository.findAllByActivatedIsTrueAndPlaceOfFuneralContainsIgnoreCase(place.toLowerCase());
    }

    public List<Necrology> findAllByActivatedIsTrueLimitedByLast(Integer last) {
        return necrologyRepository.findAllByActivatedIsTrueOrderByIdDescLimited(last);
    }

    public List<NecrologyModerationDto> findToModeration() {
        return necrologyRepository.findAllByOrderByIdDesc()
                .stream()
                .map(necrologyModerationDtoMapper::mapEntityToModerationDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        necrologyRepository.deleteById(id);
    }

    public void deleteExpiredNecrologies() {
        LocalDateTime now = LocalDateTime.now();
        necrologyRepository.deleteAllByRemovingDateIsBefore(now);
    }

    public void deleteNotActivatedNecrologies() {
        necrologyRepository.deleteAllByActivatedIsFalse();
    }

    @Transactional
    public void update(Long id, NecrologyModerationEditDto necrologyModerationEditDto) {
        Optional<Necrology> necrologyOptional = necrologyRepository.findById(id);
        necrologyOptional.ifPresent(necrology -> {
            try {
                setUpdate(necrologyModerationEditDto, necrology);
            } catch (IOException e) {
                throw new RuntimeException("File could not be saved");
            }
        });
    }

    private void setUpdate(NecrologyModerationEditDto necrologyModerationEditDto, Necrology necrology) throws IOException {
        necrology.setName(necrologyModerationEditDto.getName());
        necrology.setBirthDate(necrologyModerationEditDto.getBirthDate());
        necrology.setDeathDate(necrologyModerationEditDto.getDeathDate());
        necrology.setPlaceOfOrigin(necrologyModerationEditDto.getPlaceOfOrigin());
        necrology.setPlaceOfFuneral(necrologyModerationEditDto.getPlaceOfFuneral());
        Optional<Gender> genderByName = Gender.findGenderByName(necrologyModerationEditDto.getGender());
        genderByName.ifPresent(necrology::setGender);
        necrology.setPictureBytes(necrologyModerationEditDto.getPictureFile().getBytes());
        necrology.setTitle(necrologyModerationEditDto.getTitle());
        List<Kinship> kinship = kinshipService.findAllByNameIn(necrologyModerationEditDto.getKinship());
        necrology.setKinship(kinship);
        necrology.setAddCrossAndLate(necrologyModerationEditDto.getAddCrossAndLate());
        necrology.setRemovingDate(necrologyModerationEditDto.getRemoveDate());
        necrology.setFuneralDetails(necrologyModerationEditDto.getFuneralDetails());
        necrology.setAdditionalInfo(necrologyModerationEditDto.getAdditionalInfo());
    }

    public NecrologyModerationEditDto findToUpdate(Long id) {
        Optional<Necrology> necrologyOptional = findById(id);
        return necrologyOptional
                .map(necrologyModerationEditDtoMapper::mapEntityToEditDto)
                .orElseThrow(EntityNotFoundException::new);
    }
}
