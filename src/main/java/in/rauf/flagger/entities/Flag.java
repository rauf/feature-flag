package in.rauf.flagger.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "flag")
@Getter
@Setter
public class Flag extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"flag"}, allowSetters = true)
    private Set<Variant> variants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"flag"}, allowSetters = true)
    private Set<Segment> segments = new LinkedHashSet<>();

}
