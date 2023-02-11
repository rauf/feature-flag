package in.rauf.flagger.model.mapper;

import in.rauf.flagger.entities.DistributionEntity;
import in.rauf.flagger.model.dto.DistributionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VariantMapper.class})
public interface DistributionMapper extends EntityMapper<DistributionDTO, DistributionEntity> {

}
