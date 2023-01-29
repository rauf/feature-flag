package in.rauf.flagger.service.impl;

import in.rauf.flagger.service.RuleEvaluatorService;
import org.springframework.stereotype.Service;

@Service
public class RuleEvaluatorServiceImpl implements RuleEvaluatorService {
    @Override
    public boolean evaluate(String value, Object context) {
        return true;
    }
}
