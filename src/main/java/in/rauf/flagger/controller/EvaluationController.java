package in.rauf.flagger.controller;

import in.rauf.flagger.model.evaluation.EvaluationRequest;
import in.rauf.flagger.model.evaluation.EvaluationResult;
import in.rauf.flagger.service.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/evaluate")
    public ResponseEntity<EvaluationResult> evaluate(@RequestBody @Valid EvaluationRequest request) {
        var result = evaluationService.evaluate(request);
        return ResponseEntity.ok(result);
    }

}
