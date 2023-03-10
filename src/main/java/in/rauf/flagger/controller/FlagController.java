package in.rauf.flagger.controller;

import in.rauf.flagger.model.dto.FetchFlagsWithSegmentsResponseDTO;
import in.rauf.flagger.model.dto.FlagDTO;
import in.rauf.flagger.model.dto.FlagRequestDTO;
import in.rauf.flagger.model.dto.SaveFlagResponseDTO;
import in.rauf.flagger.service.FlagService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flags")
@CrossOrigin(origins = "*")
public class FlagController {

    private static final Logger log = LoggerFactory.getLogger(FlagController.class);
    private final FlagService flagService;

    public FlagController(FlagService flagService) {
        this.flagService = flagService;
    }

    @GetMapping
    public ResponseEntity<FetchFlagsWithSegmentsResponseDTO> fetchFlags() {
        log.debug("REST request to fetch all Flags");
        var result = flagService.findAllWithSegments();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<SaveFlagResponseDTO> createFlag(@Valid @RequestBody FlagRequestDTO flagRequestDTO) {
        log.debug("REST request to save Flag : {}", flagRequestDTO);
        var result = flagService.save(flagRequestDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{name}")
    public ResponseEntity<FlagDTO> getFlag(@PathVariable(value = "name") final String name) {
        log.debug("REST request to get Flag : {}", name);

        var result = flagService.getFlag(name);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{name}")
    public ResponseEntity<FlagDTO> updateFlag(@PathVariable(value = "name") final String name, @Valid @RequestBody FlagRequestDTO flagRequestDTO) {
        log.debug("REST request to update Flag : {}, {}", name, flagRequestDTO);

        var result = flagService.update(name, flagRequestDTO);
        return ResponseEntity.ok(result);
    }

}
