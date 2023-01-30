package in.rauf.flagger.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import in.rauf.flagger.service.RuleEvaluatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleEvaluatorServiceImpl implements RuleEvaluatorService {

    private static final Logger log = LoggerFactory.getLogger(RuleEvaluatorServiceImpl.class);

    @Override
    public boolean evaluate(String rule, Map<String, Object> context) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(rule);
        StandardEvaluationContext ruleContext = new StandardEvaluationContext(context);

        for (var entry : context.entrySet()) {
            ruleContext.setVariable(entry.getKey(), entry.getValue());
        }

        try {
            return (Boolean) exp.getValue(ruleContext);
        } catch (Exception e) {
            log.error("error in evaluating rule: {}", rule);
            return false;
        }
    }
}
