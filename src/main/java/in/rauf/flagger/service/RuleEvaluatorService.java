package in.rauf.flagger.service;

import java.util.Map;

public interface RuleEvaluatorService {
     boolean evaluate(String value, Map<String, Object> context);
}
