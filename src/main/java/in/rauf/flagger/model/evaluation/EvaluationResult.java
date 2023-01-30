package in.rauf.flagger.model.evaluation;

public record EvaluationResult(Object context, Long flagId, String flagName, String variantName, String msg) {
}
