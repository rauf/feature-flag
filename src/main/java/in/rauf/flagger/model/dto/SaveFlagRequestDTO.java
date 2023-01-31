package in.rauf.flagger.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.LinkedHashSet;
import java.util.Set;

public class SaveFlagRequestDTO {

    @NotBlank
    private String name;

    private String description;

    private Boolean enabled = true;

    @NotEmpty
    @Valid
    private Set<String> variants = new LinkedHashSet<>();

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

    public Set<String> getVariants() {
        return variants;
    }

    public void setVariants(Set<String> variants) {
        this.variants = variants;
    }
}
