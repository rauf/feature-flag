package in.rauf.flagger.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RuleEvaluatorServiceImplTest {

    private final RuleEvaluatorServiceImpl service = new RuleEvaluatorServiceImpl();

    @Test
    void evaluate() {
        assertTrue(service.evaluate("true", Map.of()));
        assertTrue(service.evaluate("3 > 2", Map.of()));
        assertFalse(service.evaluate("3 > 3", Map.of()));

        assertTrue(service.evaluate("#value", Map.of("value", true)));
        assertFalse(service.evaluate("#value", Map.of("value", false)));

        assertFalse(service.evaluate("#userId == 3 ", Map.of("userId", 4)));
        assertTrue(service.evaluate("#userId == 3 ", Map.of("userId", 3)));
        assertTrue(service.evaluate("#userName == 'abdul'", Map.of("userName", "abdul")));

    }
}