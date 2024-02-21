package pl.mt.mortalis.necrology;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.mt.mortalis.ActivationManager;
import pl.mt.mortalis.mail.MailService;
import pl.mt.mortalis.mail.Message;
import pl.mt.mortalis.necrology.dto.*;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyDisplayDtoMapper;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyFormDtoMapper;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyPreviewDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NecrologyService {
    private static final int LAST_ADDED_AMOUNT = 10;
    private final NecrologyRepository necrologyRepository;
    private final NecrologyFormDtoMapper necrologyFormDtoMapper;
    private final NecrologyDisplayDtoMapper necrologyDisplayDtoMapper;
    private final NecrologyPreviewDtoMapper necrologyPreviewDtoMapper;
    private final MailService mailService;
    private final ActivationManager activationManager;

    public NecrologyService(NecrologyRepository necrologyRepository,
                            NecrologyFormDtoMapper necrologyFormDtoMapper,
                            NecrologyDisplayDtoMapper necrologyDisplayDtoMapper,
                            NecrologyPreviewDtoMapper necrologyPreviewDtoMapper, MailService mailService, ActivationManager activationManager) {
        this.necrologyRepository = necrologyRepository;
        this.necrologyFormDtoMapper = necrologyFormDtoMapper;
        this.necrologyDisplayDtoMapper = necrologyDisplayDtoMapper;
        this.necrologyPreviewDtoMapper = necrologyPreviewDtoMapper;
        this.mailService = mailService;
        this.activationManager = activationManager;
    }

    @Transactional
    public void activate(NecrologyFormDto necrologyFormDto) {
        Necrology necrology = save(necrologyFormDto);
        String necrologyIdentifier = createNecrologyIdentifier(necrology);
        necrology.setNecrologyIdentifier(necrologyIdentifier);
        String code = activationManager.createCode();
        necrology.setCode(code);
        necrology.setActivated(false);
        sendActivationEmail(necrologyFormDto, code);
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
            throw new RuntimeException("Unable to send e-mail with activation link.");
        }
    }

    @Transactional
    public Necrology save(NecrologyFormDto necrologyFormDto) {
        return necrologyRepository.save(necrologyFormDtoMapper.map(necrologyFormDto));
    }

    public Optional<Necrology> findByIdentifier(String identifier) {
        return necrologyRepository.findByNecrologyIdentifierIs(identifier);
    }

    public Optional<Necrology> findActivated(String identifier) {
        return necrologyRepository.findActivated(identifier);
    }

    private String createNecrologyIdentifier(Necrology necrology) {
        return necrology.getName()
                .toLowerCase()
                .trim()
                .replace(" ", "-")
                + "-"
                + necrology.getId();
    }

    public NecrologyDisplayDto mapFromNecrology(Necrology necrology) {
        return necrologyDisplayDtoMapper.maptoDisplayDto(necrology);
    }

    public List<Necrology> findAllByActivatedIsTrue() {
        return necrologyRepository.findAllByActivatedIsTrue();
    }

    public List<NecrologyPreviewDto> findAllActivated() {
        List<Necrology> necrologies = findAllByActivatedIsTrue();
        return necrologies.stream()
                .map(necrologyPreviewDtoMapper::maptoPreviewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String validateCode(String code) {
        Optional<Necrology> optionalNecrology = necrologyRepository.findByCode(code);
        if (optionalNecrology.isPresent()) {
            Necrology necrology = optionalNecrology.get();
            necrology.setActivated(true);
            return necrology.getNecrologyIdentifier();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void deleteNotActivatedNecrologies() {
        necrologyRepository.deleteAllByActivatedIsFalse();
    }

    public void deleteExpiredNecrologies() {
        LocalDateTime now = LocalDateTime.now();
        necrologyRepository.deleteAllByRemovingDateIsBefore(now);
    }

    public Optional<Necrology> findById(Long id) {
        return necrologyRepository.findById(id);
    }

    public List<NecrologyPreviewDto> search(String word) {
        List<Necrology> necrologies = necrologyRepository.findByWord(word);
        return necrologies.stream()
                .map(necrologyPreviewDtoMapper::maptoPreviewDto)
                .collect(Collectors.toList());
    }

    public List<NecrologyPreviewDto> findLast10Activated() {
        List<Necrology> necrologies = necrologyRepository.findAllByActivatedIsTrueOrderByIdDescLimited(LAST_ADDED_AMOUNT);
        return necrologies.stream()
                .map(necrologyPreviewDtoMapper::maptoPreviewDto)
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
}
