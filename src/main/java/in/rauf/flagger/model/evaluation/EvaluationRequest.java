package in.rauf.flagger.model.evaluation;


public record EvaluationRequest(Object context, String id, String flagName) {

    public EvaluationRequest withId(String id) {
        return new EvaluationRequest(this.context, id, this.flagName);
    }
}
