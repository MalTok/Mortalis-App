package pl.mt.mortalis.candle;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.mt.mortalis.mail.ActivationManager;
import pl.mt.mortalis.candle.dto.CandleFormDto;
import pl.mt.mortalis.mail.MailService;
import pl.mt.mortalis.mail.Message;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.NecrologyService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CandleService {
    private static final int CANDLE_VALID_MAX_DAYS = 30;
    private final CandleRepository candleRepository;
    private final MailService mailService;
    private final ActivationManager activationManager;
    private final NecrologyService necrologyService;

    public CandleService(CandleRepository candleRepository,
                         MailService mailService,
                         ActivationManager activationManager,
                         NecrologyService necrologyService) {
        this.candleRepository = candleRepository;
        this.mailService = mailService;
        this.activationManager = activationManager;
        this.necrologyService = necrologyService;
    }

    public void activate(CandleFormDto candleformDto) {
        String code = activationManager.createCode();
        Candle candle = createCandle(candleformDto, code);
        candleRepository.save(candle);
        try {
            sendEmail(candleformDto, code);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send e-mail with activation link.");
        }
    }

    private void sendEmail(CandleFormDto candleformDto, String code) throws MessagingException {
        mailService.sendActivationEmail(
                new Message(
                        candleformDto.getEmail(),
                        "Aktywacja - zapal świeczkę",
                        activationManager.createCandleActivationMessageText(code)
                )
        );
    }

    private Candle createCandle(CandleFormDto candleformDto, String code) {
        Candle candle = new Candle();
        LocalDateTime startDateTime = LocalDateTime.now();
        candle.setStartDateTime(startDateTime);
        candle.setExpirationDateTime(startDateTime.plusDays(CANDLE_VALID_MAX_DAYS).withHour(23).withMinute(59).withSecond(59));
        candle.setCode(code);
        candle.setActivated(false);
        Optional<Necrology> optionalNecrology = necrologyService.findById(candleformDto.getNecrologyId());
        optionalNecrology.ifPresent(candle::setNecrology);
        return candle;
    }

    @Transactional
    public String validateCode(String code) {
        Optional<Candle> optionalCandle = candleRepository.findByCode(code);
        if (optionalCandle.isPresent()) {
            Candle candle = optionalCandle.get();
            candle.setActivated(true);
            return candle.getNecrology().getNecrologyIdentifier();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    public void deleteExpiredCandles() {
        LocalDateTime now = LocalDateTime.now();
        candleRepository.deleteAllByExpirationDateTimeBefore(now);
    }

    @Transactional
    public void deleteNotActivatedCandles() {
        candleRepository.deleteAllByActivatedIsFalse();
    }
}
