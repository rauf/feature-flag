package in.rauf.flagger.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class SegmentDTO {
    @NotBlank
    private String name;

    private Integer priority;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer rolloutPercentage;

    @Column(name = "cons")
    private String constraint;

    @Valid
    @NotEmpty
    private Set<DistributionDTO> distributions = new LinkedHashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getRolloutPercentage() {
        return rolloutPercentage;
    }

    public void setRolloutPercentage(Integer rolloutPercentage) {
        this.rolloutPercentage = rolloutPercentage;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public Set<DistributionDTO> getDistributions() {
        return distributions;
    }

    public void setDistributions(Set<DistributionDTO> distributions) {
        this.distributions = distributions;
    }
}
