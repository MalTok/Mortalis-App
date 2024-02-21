package pl.mt.mortalis;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class ActivationManager {
    public String createCode() {
        return UUID.randomUUID().toString();
    }

    private String createActivationMessageText(String code, String uriString) {
        return  "W celu weryfikacji kliknij lub skopiuj i wklej w przeglądarce ten link: " +
                UriComponentsBuilder.fromUriString(uriString)
                        .queryParam("code", code)
                        .build();
    }

    public String createCandleActivationMessageText(String code) {
        return createActivationMessageText(code, "http://localhost:8080/zapal-swieczke/weryfikacja");
    }

    public String createCondolencesActivationMessageText(String code) {
        return createActivationMessageText(code, "http://localhost:8080/kondolencje/weryfikacja");
    }

    public String createNecrologyActivationMessageText(String code) {
        return createActivationMessageText(code, "http://localhost:8080/nekrolog/weryfikacja");
    }
}
