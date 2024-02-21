package pl.mt.mortalis.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mt.mortalis.condolences.CondolencesService;

@RequestMapping("/administracja")
@Controller
public class AdministrationController {
    private final CondolencesService condolencesService;

    public AdministrationController(CondolencesService condolencesService) {
        this.condolencesService = condolencesService;
    }

    @GetMapping("/moderacja/kondolencje")
    String moderateCondolences(Model model) {
        model.addAttribute("condolences", condolencesService.findValidCondolences());
        return "/administration/moderate-condolences";
    }

    @PostMapping("/moderacja/kondolencje/{id}/usun")
    String deleteCondolences(@PathVariable("id") Long id) {
        condolencesService.deleteById(id);
        return "redirect:/administracja/moderacja/kondolencje";
    }
}
