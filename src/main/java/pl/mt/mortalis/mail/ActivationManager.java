package pl.mt.mortalis.mail;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class ActivationManager {
    public static final String MESSAGING_EXCEPTION_TEXT = "Unable to send e-mail with activation link";

    public String createCode() {
        return UUID.randomUUID().toString();
    }

    private String createActivationMessageText(String code, String uriString) {
        UriComponents uriLink = UriComponentsBuilder.fromUriString(uriString)
                .queryParam("code", code)
                .build();
        return  "<b>MORTALIS baza nekrologów</b><br/><br/>" +
                "W celu weryfikacji <strong>kliknij lub skopiuj</strong> i wklej w przeglądarce ten link:<br/><br/>" +
                "<a href=&quot;" + uriLink + "&quot;>" +
                uriLink +
                "</a>";
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
