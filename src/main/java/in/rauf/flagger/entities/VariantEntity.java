package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "variant")
@Getter
@Setter
public class VariantEntity extends BaseEntity {


    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "flag_id")
    private FlagEntity flag;

}