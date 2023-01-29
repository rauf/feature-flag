package in.rauf.flagger.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "constraint")
@Getter
@Setter
public class Constraint extends BaseEntity {

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @OneToOne(mappedBy = "constraint", optional = false)
    private Segment segment;

}