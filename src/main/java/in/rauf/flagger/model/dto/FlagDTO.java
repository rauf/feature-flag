package in.rauf.flagger.model.dto;

import java.util.Set;

public record FlagDTO(
        String name,
        String description,
        Boolean enabled,
        Set<VariantDTO> variants,
        Set<SegmentDTO> segments
) {
}