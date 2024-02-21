package pl.mt.mortalis.page;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping("/strona")
@Controller
public class PageController {
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/{url}")
    String getPage(@PathVariable String url, Model model) {
        Optional<Page> pageOptional = pageService.findByUrl(url);
        if (pageOptional.isPresent()) {
            model.addAttribute("page", pageOptional.get());
            return "page";
        } else {
            throw new EntityNotFoundException("This page cannot be found.");
        }
    }
}
