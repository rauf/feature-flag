package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "distribution")
@Getter
@Setter
public class DistributionEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "percent", nullable = false)
    private Integer percent;

    @ManyToOne
    @JoinColumn(name = "flag_id", nullable = false)
    private FlagEntity flag;

    @ManyToOne
    @JoinColumn(name = "segment_id", nullable = false)
    private SegmentEntity segment;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private VariantEntity variant;

}