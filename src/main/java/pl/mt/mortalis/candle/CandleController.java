package pl.mt.mortalis.candle;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.mt.mortalis.candle.dto.CandleFormDto;

@RequestMapping("/zapal-swieczke")
@Controller
public class CandleController {
    private final CandleService candleService;

    public CandleController(CandleService candleService) {
        this.candleService = candleService;
    }

    @PostMapping("/aktywacja")
    public String lightCandle(@Valid @ModelAttribute("candle") CandleFormDto candleFormDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("candle", candleFormDto);
            return "/candle/candle-form";
        } else {
            candleService.activate(candleFormDto);
            return "redirect:/zapal-swieczke/nieaktywny";
        }
    }

    @GetMapping("/nieaktywny")
    String notActive() {
        return "/activation/not-activated";
    }

    @GetMapping("/weryfikacja")
    public String validateCode(@RequestParam("code") String code) {
        try {
            String identifier = candleService.validateCode(code);
            return "redirect:/nekrolog/" + identifier;
        } catch (EntityNotFoundException e) {
            return "/activation/activation-error";
        }
    }
}
