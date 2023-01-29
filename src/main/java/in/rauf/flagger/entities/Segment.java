package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "segment")
@Getter
@Setter

public class Segment extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "rank", nullable = false)
    private Integer rank;

    @NotNull
    @Column(name = "rollout_percentage", nullable = false)
    private Integer rolloutPercentage;

    @ManyToOne
    @JoinColumn(name = "flag_id")
    private Flag flag;

    @OneToOne
    private Constraint constraint;

}