package pl.mt.mortalis.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mt.mortalis.admin.dto.CondolencesModerationEditDto;
import pl.mt.mortalis.admin.dto.NecrologyModerationEditDto;
import pl.mt.mortalis.condolences.CondolencesService;
import pl.mt.mortalis.necrology.NecrologyService;

@RequestMapping("/administracja")
@Controller
public class AdministrationController {
    private final CondolencesService condolencesService;
    private final NecrologyService necrologyService;

    public AdministrationController(CondolencesService condolencesService, NecrologyService necrologyService) {
        this.condolencesService = condolencesService;
        this.necrologyService = necrologyService;
    }

    @GetMapping("/moderacja/kondolencje")
    String moderateCondolences(Model model) {
        model.addAttribute("condolences", condolencesService.findCondolencesToModeration());
        return "/administration/moderate-condolences";
    }

    @PostMapping("/moderacja/kondolencje/{id}/usun")
    String deleteCondolences(@PathVariable("id") Long id) {
        condolencesService.deleteById(id);
        return "redirect:/administracja/moderacja/kondolencje";
    }

    @PostMapping("/moderacja/kondolencje/{id}/edytuj")
    String editCondolences(@PathVariable("id") Long id) {
        return "redirect:/administracja/moderacja/kondolencje/" + id + "/aktualizuj";
    }

    @GetMapping("/moderacja/kondolencje/{id}/aktualizuj")
    String updateCondolencesForm(@PathVariable("id") Long id, Model model) {
        CondolencesModerationEditDto condolencesModerationEditDto = condolencesService.getToUpdate(id);
        model.addAttribute("condolencesEdit", condolencesModerationEditDto);
        return "/administration/moderate-condolences-edit-form";
    }

    @PostMapping("/moderacja/kondolencje/{id}/aktualizuj")
    String updateCondolences(@PathVariable("id") Long id,
                             @ModelAttribute("condolencesEdit") CondolencesModerationEditDto condolencesModerationEditDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("condolencesEdit", condolencesModerationEditDto);
            return "/administration/moderate-condolences-edit-form";
        } else {
            condolencesService.update(id, condolencesModerationEditDto);
        }
        return "redirect:/administracja/moderacja/kondolencje";
    }

    @GetMapping("/moderacja/nekrologi")
    String moderateNecrologies(Model model) {
        model.addAttribute("necrologies", necrologyService.findNecrologiesToModeration());
        return "/administration/moderate-necrologies";
    }

    @PostMapping("/moderacja/nekrologi/{id}/usun")
    String deleteNecrology(@PathVariable("id") Long id) {
        necrologyService.deleteById(id);
        return "redirect:/administracja/moderacja/nekrologi";
    }

    @PostMapping("/moderacja/nekrologi/{id}/edytuj")
    String editNecrology(@PathVariable("id") Long id) {
        return "redirect:/administracja/moderacja/nekrologi/" + id + "/aktualizuj";
    }

    @GetMapping("/moderacja/nekrologi/{id}/aktualizuj")
    String updateNecrologyForm(@PathVariable("id") Long id, Model model) {
        NecrologyModerationEditDto necrologyModerationEditDto = necrologyService.getToUpdate(id);
        model.addAttribute("necrologyEdit", necrologyModerationEditDto);
        return "/administration/moderate-necrology-edit-form";
    }

    @PostMapping("/moderacja/nekrologi/{id}/aktualizuj")
    String updateNecrology(@PathVariable("id") Long id,
                           @ModelAttribute("necrologyEdit") NecrologyModerationEditDto necrologyModerationEditDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("necrologyEdit", necrologyModerationEditDto);
            return "/administration/moderate-necrology-edit-form";
        } else {
            necrologyService.update(id, necrologyModerationEditDto);
        }
        return "redirect:/administracja/moderacja/nekrologi";
    }
}
