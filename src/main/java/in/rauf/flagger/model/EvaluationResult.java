package in.rauf.flagger.model;

public record EvaluationResult(Object context, Long flagId, String flagName, String variantName, String msg) {
}
