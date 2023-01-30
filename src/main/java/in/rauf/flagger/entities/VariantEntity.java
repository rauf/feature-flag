package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "variant")
public class VariantEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "flag_id")
    private FlagEntity flag;

    public VariantEntity(String name, FlagEntity flag) {
        this.name = name;
        this.flag = flag;
    }

    public VariantEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FlagEntity getFlag() {
        return flag;
    }

    public void setFlag(FlagEntity flag) {
        this.flag = flag;
    }
}