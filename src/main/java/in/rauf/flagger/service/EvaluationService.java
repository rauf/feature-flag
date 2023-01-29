package in.rauf.flagger.service;

import in.rauf.flagger.model.EvaluationRequest;
import in.rauf.flagger.model.EvaluationResult;

public interface EvaluationService {

    EvaluationResult evaluate(EvaluationRequest request);
}
