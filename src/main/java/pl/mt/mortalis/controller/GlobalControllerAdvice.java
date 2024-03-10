package pl.mt.mortalis.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.mt.mortalis.kinship.Kinship;
import pl.mt.mortalis.kinship.KinshipService;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final KinshipService kinshipService;

    public GlobalControllerAdvice(KinshipService kinshipService) {
        this.kinshipService = kinshipService;
    }

    @ModelAttribute("kinshipList")
    List<Kinship> kinshipList() {
        return kinshipService.findAll();
    }
}
