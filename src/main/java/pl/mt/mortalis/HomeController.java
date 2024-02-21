package pl.mt.mortalis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mt.mortalis.necrology.NecrologyService;
import pl.mt.mortalis.necrology.dto.NecrologyPreviewDto;

import java.util.List;

@Controller
public class HomeController {
    private final NecrologyService necrologyService;

    public HomeController(NecrologyService necrologyService) {
        this.necrologyService = necrologyService;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("necrologies", necrologyService.findLast10Activated());
        return "index";
    }

    @GetMapping("/wszystkie")
    String showAll(Model model) {
        model.addAttribute("necrologies", necrologyService.findAllActivated());
        return "/necrology/all-necrologies";
    }

    @GetMapping("/szukaj")
    String search(@RequestParam(value = "word") String word, Model model) {
        List<NecrologyPreviewDto> foundNecrologies = necrologyService.search(word);
        model.addAttribute("necrologies", foundNecrologies);
        return "/necrology/found-necrologies";
    }

    @GetMapping("/administruj")
    String showAdminPage() {
        return "/administration/administration-home-page";
    }
}
