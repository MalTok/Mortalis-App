package pl.mt.mortalis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mt.mortalis.necrology.Necrology;
import pl.mt.mortalis.necrology.NecrologyService;
import pl.mt.mortalis.necrology.dto.NecrologyApiDto;
import pl.mt.mortalis.necrology.dto.mapper.NecrologyApiDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/v1/necrology", produces = {MediaType.APPLICATION_JSON_VALUE})
@Controller
public class ApiController {
    private final NecrologyService necrologyService;
    private final NecrologyApiDtoMapper necrologyApiDtoMapper;

    public ApiController(NecrologyService necrologyService, NecrologyApiDtoMapper necrologyApiDtoMapper) {
        this.necrologyService = necrologyService;
        this.necrologyApiDtoMapper = necrologyApiDtoMapper;
    }

    @GetMapping
    ResponseEntity<List<NecrologyApiDto>> getAll(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String place,
                                                 @RequestParam(required = false) Integer last) {
        List<Necrology> necrologyList;
        if (name != null && place != null) {
            necrologyList = necrologyService.findAllByNameAndPlaceAndActivated(name, place);
        } else if (name != null) {
            necrologyList = necrologyService.findAllByNameAndActivated(name);
        } else if (place != null) {
            necrologyList = necrologyService.findAllByPlaceAndActivated(place);
        } else if (last != null) {
            necrologyList = necrologyService.findAllByActivatedIsTrueLimitedByLast(last);
        } else {
            necrologyList = necrologyService.findAllByActivatedIsTrue();
        }
        return ResponseEntity.ok(necrologyList
                .stream()
                .map(necrologyApiDtoMapper::mapToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<NecrologyApiDto> getSingleNecrology(@PathVariable Long id) {
        Optional<NecrologyApiDto> optionalNecrology = necrologyService.findById(id)
                .map(necrologyApiDtoMapper::mapToDto);
        return optionalNecrology.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    ResponseEntity<Integer> count() {
        return ResponseEntity.ok(necrologyService.findAllByActivatedIsTrue().size());
    }
}
