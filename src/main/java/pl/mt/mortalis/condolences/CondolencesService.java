package pl.mt.mortalis.condolences;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.mt.mortalis.admin.dto.CondolencesModerationEditDto;
import pl.mt.mortalis.admin.dto.mapper.CondolencesModerationDtoMapper;
import pl.mt.mortalis.admin.dto.mapper.CondolencesModerationEditDtoMapper;
import pl.mt.mortalis.mail.ActivationManager;
import pl.mt.mortalis.admin.dto.CondolencesModerationDto;
import pl.mt.mortalis.condolences.dto.CondolencesFormDto;
import pl.mt.mortalis.mail.MailService;
import pl.mt.mortalis.mail.Message;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.NecrologyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CondolencesService {
    private final CondolencesRepository condolencesRepository;
    private final MailService mailService;
    private final ActivationManager activationManager;
    private final NecrologyService necrologyService;
    private final CondolencesModerationDtoMapper condolencesModerationDtoMapper;
    private final CondolencesModerationEditDtoMapper condolencesModerationEditDtoMapper;

    public CondolencesService(CondolencesRepository condolencesRepository,
                              MailService mailService,
                              ActivationManager activationManager,
                              NecrologyService necrologyService,
                              CondolencesModerationDtoMapper condolencesModerationDtoMapper,
                              CondolencesModerationEditDtoMapper condolencesModerationEditDtoMapper) {
        this.condolencesRepository = condolencesRepository;
        this.mailService = mailService;
        this.activationManager = activationManager;
        this.necrologyService = necrologyService;
        this.condolencesModerationDtoMapper = condolencesModerationDtoMapper;
        this.condolencesModerationEditDtoMapper = condolencesModerationEditDtoMapper;
    }

    public void activate(CondolencesFormDto condolencesFormDto) {
        String code = activationManager.createCode();
        Condolences condolences = createCondolences(condolencesFormDto, code);
        condolencesRepository.save(condolences);
        try {
            sendEmail(condolencesFormDto, code);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send e-mail with activation link.");
        }
    }

    private void sendEmail(CondolencesFormDto condolencesFormDto, String code) throws MessagingException {
        mailService.sendActivationEmail(
                new Message(
                        condolencesFormDto.getFromEmail(),
                        "Aktywacja - opublikuj kondolencje",
                        activationManager.createCondolencesActivationMessageText(code)
                )
        );
    }

    private Condolences createCondolences(CondolencesFormDto condolencesFormDto, String code) {
        Condolences condolences = new Condolences();
        if (!condolencesFormDto.getFromName().isEmpty()) {
            condolences.setFromName(condolencesFormDto.getFromName());
        } else {
            condolences.setFromName("Anonim");
        }
        condolences.setFromEmail(condolencesFormDto.getFromEmail());
        condolences.setMessage(condolencesFormDto.getMessage());
        Optional<Necrology> optionalNecrology = necrologyService.findById(condolencesFormDto.getNecrologyId());
        optionalNecrology.ifPresent(condolences::setNecrology);
        condolences.setCode(code);
        condolences.setActivated(false);
        return condolences;
    }

    @Transactional
    public String validateCode(String code) {
        Optional<Condolences> optionalCondolences = condolencesRepository.findByCode(code);
        if (optionalCondolences.isPresent()) {
            Condolences condolences = optionalCondolences.get();
            condolences.setActivated(true);
            return condolences.getNecrology().getNecrologyIdentifier();
        } else {
            throw new EntityNotFoundException();
        }
    }

    public List<CondolencesModerationDto> findCondolencesToModeration() {
        return condolencesRepository.findAllByOrderByIdDesc()
                .stream()
                .map(condolencesModerationDtoMapper::mapToModerationDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotActivatedCondolences() {
        condolencesRepository.deleteAllByActivatedIsFalse();
    }

    public void deleteById(Long id) {
        condolencesRepository.deleteById(id);
    }

    public CondolencesModerationEditDto getToUpdate(Long id) {
        Optional<Condolences> condolencesOptional = condolencesRepository.findById(id);
        return condolencesOptional
                .map(condolencesModerationEditDtoMapper::mapToEditDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void update(Long id, CondolencesModerationEditDto condolencesModerationEditDto) {
        Optional<Condolences> condolencesOptional = condolencesRepository.findById(id);
        condolencesOptional.ifPresent(condolences -> {
            condolences.setFromName(condolencesModerationEditDto.getFromName());
            condolences.setMessage(condolencesModerationEditDto.getMessage());
        });
    }
}
