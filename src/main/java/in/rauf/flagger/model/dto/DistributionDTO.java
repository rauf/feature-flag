package in.rauf.flagger.model.dto;

import jakarta.validation.constraints.NotNull;

public class DistributionDTO {
    @NotNull
    private String name;

    @NotNull
    private Integer percent;

    private String variant;

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

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }
}
