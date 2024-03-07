package pl.mt.mortalis.necrology;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mt.mortalis.candle.dto.CandleFormDto;
import pl.mt.mortalis.condolences.dto.CondolencesFormDto;
import pl.mt.mortalis.necrology.dto.NecrologyDisplayDto;
import pl.mt.mortalis.necrology.dto.NecrologyFormDto;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/nekrolog")
@Controller
public class NecrologyController {
    private final NecrologyService necrologyService;

    public NecrologyController(NecrologyService necrologyService) {
        this.necrologyService = necrologyService;
    }

    @GetMapping("/dodaj")
    String addForm(Model model) {
        model.addAttribute("necrology", new NecrologyFormDto());
        return "/necrology/necrology-form";
    }

    @PostMapping("/dodaj")
    String add(@Valid @ModelAttribute("necrology") NecrologyFormDto necrologyFormDto,
               BindingResult bindingResult,
               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("necrology", necrologyFormDto);
            return "/necrology/necrology-form";
        } else {
            try {
                necrologyService.activate(necrologyFormDto);
            } catch (IOException e) {
                throw new RuntimeException("File could not be saved");
            }
            return "redirect:/nekrolog/nieaktywny";
        }
    }

    @GetMapping("/nieaktywny")
    String notActive() {
        return "/activation/not-activated";
    }

    @GetMapping("/weryfikacja")
    public String validateCode(@RequestParam("code") String code) {
        try {
            String identifier = necrologyService.validateCode(code);
            return "redirect:/nekrolog/" + identifier;
        } catch (EntityNotFoundException e) {
            return "/activation/activation-error";
        }
    }

    @GetMapping("/{identifier}")
    String show(@PathVariable String identifier, Model model) {
        Optional<Necrology> optionalNecrology = necrologyService.findActivated(identifier);
        if (optionalNecrology.isPresent()) {
            Necrology necrology = optionalNecrology.get();
            NecrologyDisplayDto necrologyDisplayDto = necrologyService.mapFromNecrology(necrology);
            model.addAttribute("necrology", necrologyDisplayDto);
            return "/necrology/single-necrology";
        } else {
            return "/activation/not-activated";
        }
    }

    @GetMapping("/{identifier}/zapal-swieczke")
    String lightCandleForm(@PathVariable("identifier") String identifier, Model model) {
        Optional<Necrology> optionalNecrology = necrologyService.findByIdentifier(identifier);
        if (optionalNecrology.isPresent()) {
            Necrology necrology = optionalNecrology.get();
            CandleFormDto candleformDto = new CandleFormDto();
            candleformDto.setNecrologyId(necrology.getId());
            model.addAttribute("candle", candleformDto);
            return "/candle/candle-form";
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping("/{identifier}/dodaj-kondolencje")
    String addCondolencesForm(@PathVariable String identifier, Model model) {
        Optional<Necrology> optionalNecrology = necrologyService.findByIdentifier(identifier);
        if (optionalNecrology.isPresent()) {
            Necrology necrology = optionalNecrology.get();
            CondolencesFormDto condolencesFormDto = new CondolencesFormDto();
            condolencesFormDto.setNecrologyId(necrology.getId());
            model.addAttribute("condolences", condolencesFormDto);
            return "/condolences/condolences-form";
        } else {
            throw new EntityNotFoundException();
        }
    }
}
