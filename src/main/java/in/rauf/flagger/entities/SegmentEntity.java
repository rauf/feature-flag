package in.rauf.flagger.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "segment")
public class SegmentEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @NotNull
    @Column(name = "rollout_percentage", nullable = false)
    private Integer rolloutPercentage;

    @ManyToOne
    @JoinColumn(name = "flag_id")
    private FlagEntity flag;

    @Column(name = "cons")
    private String constraint;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"segment"}, allowSetters = true)
    private Set<DistributionEntity> distributions = new LinkedHashSet<>();

    public SegmentEntity(String name, Integer priority, Integer rolloutPercentage, FlagEntity flag, String constraint, Set<DistributionEntity> distributions) {
        this.name = name;
        this.priority = priority;
        this.rolloutPercentage = rolloutPercentage;
        this.flag = flag;
        this.constraint = constraint;
        this.distributions = distributions;
    }

    public SegmentEntity() {

    }

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

    public FlagEntity getFlag() {
        return flag;
    }

    public void setFlag(FlagEntity flag) {
        this.flag = flag;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public Set<DistributionEntity> getDistributions() {
        return distributions;
    }

    public void setDistributions(Set<DistributionEntity> distributions) {
        this.distributions = distributions;
    }
}