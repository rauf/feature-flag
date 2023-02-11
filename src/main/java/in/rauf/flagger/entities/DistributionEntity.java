package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "distribution")
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


    public DistributionEntity(String name, Integer percent, FlagEntity flag, SegmentEntity segment, VariantEntity variant) {
        this.name = name;
        this.percent = percent;
        this.flag = flag;
        this.segment = segment;
        this.variant = variant;
    }

    public DistributionEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public FlagEntity getFlag() {
        return flag;
    }

    public void setFlag(FlagEntity flag) {
        this.flag = flag;
    }

    public SegmentEntity getSegment() {
        return segment;
    }

    public void setSegment(SegmentEntity segment) {
        this.segment = segment;
    }

    public VariantEntity getVariant() {
        return variant;
    }

    public void setVariant(VariantEntity variant) {
        this.variant = variant;
    }
}