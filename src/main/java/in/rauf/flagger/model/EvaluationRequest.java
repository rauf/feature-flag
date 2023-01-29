package in.rauf.flagger.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationRequest {

    private Object context;
    private String id;
    private String flagName;

}
