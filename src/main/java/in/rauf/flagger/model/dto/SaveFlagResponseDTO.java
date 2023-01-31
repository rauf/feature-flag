package in.rauf.flagger.model.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class SaveFlagResponseDTO {

    private Long id;
    private String name;

    private String description;

    private Boolean enabled = true;

    private Set<VariantDTO> variants = new LinkedHashSet<>();


    public SaveFlagResponseDTO(Long id, String name, String description, Boolean enabled, Set<VariantDTO> variants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.variants = variants;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(Set<VariantDTO> variants) {
        this.variants = variants;
    }
}
