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
    private final NecrologyService necrologyService;
    private final ActivationManager activationManager;

    public CandleService(CandleRepository candleRepository,
                         MailService mailService,
                         NecrologyService necrologyService,
                         ActivationManager activationManager) {
        this.candleRepository = candleRepository;
        this.mailService = mailService;
        this.necrologyService = necrologyService;
        this.activationManager = activationManager;
    }

    public void startActivation(CandleFormDto candleformDto) {
        try {
            String code = activationManager.createCode();
            Candle candle = createCandle(candleformDto, code);
            candleRepository.save(candle);
            sendEmail(candleformDto, code);
        } catch (MessagingException e) {
            throw new RuntimeException(ActivationManager.MESSAGING_EXCEPTION_TEXT);
        }
    }

    private Candle createCandle(CandleFormDto candleformDto, String code) {
        Candle candle = new Candle();
        LocalDateTime startDateTime = LocalDateTime.now();
        candle.setStartDateTime(startDateTime);
        candle.setExpirationDateTime(
                startDateTime
                        .plusDays(CANDLE_VALID_MAX_DAYS)
                        .withHour(23)
                        .withMinute(59)
                        .withSecond(59)
        );
        candle.setCode(code);
        candle.setActivated(false);
        Optional<Necrology> optionalNecrology = necrologyService.findById(candleformDto.getNecrologyId());
        optionalNecrology.ifPresent(candle::setNecrology);
        return candle;
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

    @Transactional
    public String validateCode(String code) {
        Optional<Candle> optionalCandle = candleRepository.findByCode(code);
        return optionalCandle
                .map(this::activate)
                .orElseThrow(EntityNotFoundException::new);
    }

    private String activate(Candle candle) {
        candle.setActivated(true);
        return candle.getNecrology().getNecrologyIdentifier();
    }

    public void deleteExpiredCandles() {
        LocalDateTime now = LocalDateTime.now();
        candleRepository.deleteAllByExpirationDateTimeBefore(now);
    }

    public void deleteNotActivatedCandles() {
        candleRepository.deleteAllByActivatedIsFalse();
    }
}
