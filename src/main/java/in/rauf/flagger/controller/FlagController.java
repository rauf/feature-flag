package in.rauf.flagger.controller;

import in.rauf.flagger.model.dto.FlagDTO;
import in.rauf.flagger.service.FlagService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flag")
public class FlagController {

    private static final Logger log = LoggerFactory.getLogger(FlagController.class);
    private final FlagService flagService;

    public FlagController(FlagService flagService) {
        this.flagService = flagService;
    }

    @PostMapping
    public ResponseEntity<FlagDTO> createFlag(@Valid @RequestBody FlagDTO flagDTO) {
        log.debug("REST request to save Flag : {}", flagDTO);
        var result = flagService.save(flagDTO);
        return ResponseEntity.ok(result);
    }
}
