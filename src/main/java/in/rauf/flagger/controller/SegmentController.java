package in.rauf.flagger.controller;

import in.rauf.flagger.model.dto.CreateSegmentRequest;
import in.rauf.flagger.service.SegmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flag")
public class SegmentController {

    private static final Logger log = LoggerFactory.getLogger(SegmentController.class);

    private final SegmentService segmentService;

    public SegmentController(SegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @PostMapping("/{flagName}/segments")
    public ResponseEntity<?> createSegments(@PathVariable("flagName") String flagName, @Valid @RequestBody CreateSegmentRequest createSegmentRequest) {
        log.debug("REST request to save Flag : {}, segments: {}", flagName, createSegmentRequest);
        var result = segmentService.save(flagName, createSegmentRequest);
        return ResponseEntity.ok(result);
    }
}
