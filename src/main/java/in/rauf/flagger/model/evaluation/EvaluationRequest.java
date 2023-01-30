package in.rauf.flagger.model.evaluation;


import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record EvaluationRequest(Map<String, Object> context, String id, @NotBlank String flagName) {

    public EvaluationRequest withId(String id) {
        return new EvaluationRequest(this.context, id, this.flagName);
    }
}
