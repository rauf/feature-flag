package in.rauf.flagger.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class VariantDTO {

    @NotBlank
    private final String name;

    public VariantDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
