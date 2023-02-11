package in.rauf.flagger.model.mapper;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.model.dto.FlagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VariantMapper.class})
public interface FlagMapper extends EntityMapper<FlagDTO, FlagEntity> {

}

