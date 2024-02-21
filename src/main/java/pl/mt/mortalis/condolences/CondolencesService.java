package pl.mt.mortalis.condolences;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.mt.mortalis.ActivationManager;
import pl.mt.mortalis.admin.CondolencesModerationDto;
import pl.mt.mortalis.condolences.dto.CondolencesFormDto;
import pl.mt.mortalis.mail.MailService;
import pl.mt.mortalis.mail.Message;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CondolencesService {
    private final CondolencesRepository condolencesRepository;
    private final MailService mailService;
    private final ActivationManager activationManager;

    public CondolencesService(CondolencesRepository condolencesRepository,
                              MailService mailService,
                              ActivationManager activationManager) {
        this.condolencesRepository = condolencesRepository;
        this.mailService = mailService;
        this.activationManager = activationManager;
    }

    public void activate(CondolencesFormDto condolencesFormDto) {
        String code = activationManager.createCode();
        Condolences condolences = new Condolences();
        condolences.setFromName(condolencesFormDto.getFromName());
        condolences.setFromEmail(condolencesFormDto.getFromEmail());
        condolences.setMessage(condolencesFormDto.getMessage());
        condolences.setCode(code);
        condolences.setActivated(false);
        condolencesRepository.save(condolences);
        try {
            mailService.sendActivationEmail(
                    new Message(
                            condolences.getFromEmail(),
                            "Aktywacja - opublikuj kondolencje",
                            activationManager.createCondolencesActivationMessageText(code)
                    )
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send e-mail with activation link.");
        }
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

    public List<CondolencesModerationDto> findValidCondolences() {
        return condolencesRepository.findAllByActivatedTrueOrderByIdDesc()
                .stream()
                .map(this::mapCondolencestoModerationDto)
                .collect(Collectors.toList());
    }

    private CondolencesModerationDto mapCondolencestoModerationDto(Condolences condolences) {
        return new CondolencesModerationDto(
                condolences.getId(),
                condolences.getFromName(),
                condolences.getMessage()
        );
    }

    @Transactional
    public void deleteNotActivatedCondolences() {
        condolencesRepository.deleteAllByActivatedIsFalse();
    }

    public void deleteById(Long id) {
        condolencesRepository.deleteById(id);
    }
}
