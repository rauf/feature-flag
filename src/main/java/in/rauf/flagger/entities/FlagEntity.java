package in.rauf.flagger.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "flag")
public class FlagEntity extends BaseEntity {

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
    private Set<VariantEntity> variants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"flag"}, allowSetters = true)
    private Set<SegmentEntity> segments = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<VariantEntity> getVariants() {
        return variants;
    }

    public void setVariants(Set<VariantEntity> variants) {
        this.variants = variants;
    }

    public Set<SegmentEntity> getSegments() {
        return segments;
    }

    public void setSegments(Set<SegmentEntity> segments) {
        this.segments = segments;
    }
}
