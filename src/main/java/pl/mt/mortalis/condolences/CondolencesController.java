package pl.mt.mortalis.condolences;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mt.mortalis.condolences.dto.CondolencesFormDto;

@RequestMapping("/kondolencje")
@Controller
public class CondolencesController {
    private final CondolencesService condolencesService;

    public CondolencesController(CondolencesService condolencesService) {
        this.condolencesService = condolencesService;
    }

    @PostMapping("/aktywacja")
    public String addCondolences(@Valid @ModelAttribute("condolences") CondolencesFormDto condolencesFormDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("condolences", condolencesFormDto);
            return "/condolences/condolences-form";
        } else {
            condolencesService.activate(condolencesFormDto);
            return "redirect:/";
        }
    }

    @GetMapping("/nieaktywny")
    String notActive() {
        return "/activation/not-activated";
    }

    @GetMapping("/weryfikacja")
    public String validateCode(@RequestParam("code") String code) {
        try {
            String identifier = condolencesService.validateCode(code);
            return "redirect:/nekrolog/" + identifier;
        } catch (EntityNotFoundException e) {
            return "/activation/activation-error";
        }
    }
}
