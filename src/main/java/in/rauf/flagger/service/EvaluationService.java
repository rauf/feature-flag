package in.rauf.flagger.service;

import in.rauf.flagger.model.evaluation.EvaluationRequest;
import in.rauf.flagger.model.evaluation.EvaluationResult;

public interface EvaluationService {

    EvaluationResult evaluate(EvaluationRequest request);
}
