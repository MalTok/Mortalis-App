package pl.mt.mortalis.schedule;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mt.mortalis.candle.CandleService;
import pl.mt.mortalis.condolences.CondolencesService;
import pl.mt.mortalis.necrology.NecrologyService;

@Transactional
@Component
public class OutdatedDataEraserService {
    private final CandleService candleService;
    private final CondolencesService condolencesService;
    private final NecrologyService necrologyService;

    public OutdatedDataEraserService(CandleService candleService,
                                     CondolencesService condolencesService,
                                     NecrologyService necrologyService) {
        this.candleService = candleService;
        this.condolencesService = condolencesService;
        this.necrologyService = necrologyService;
    }

    //@Scheduled(cron = "1 * * * * ?") for testing purpose
    @Scheduled(cron = "0 0 12 * * ?")
    public void executeJob() {
        candleService.deleteExpiredCandles();
        candleService.deleteNotActivatedCandles();

        condolencesService.deleteNotActivatedCondolences();

        necrologyService.deleteExpiredNecrologies();
        necrologyService.deleteNotActivatedNecrologies();
    }
}
