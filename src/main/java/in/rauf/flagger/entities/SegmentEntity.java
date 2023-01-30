package in.rauf.flagger.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "segment")
@Getter
@Setter
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
}