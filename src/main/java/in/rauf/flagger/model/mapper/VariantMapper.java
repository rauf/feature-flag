package in.rauf.flagger.model.mapper;

import in.rauf.flagger.entities.VariantEntity;
import in.rauf.flagger.model.dto.VariantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariantMapper extends EntityMapper<VariantDTO, VariantEntity> {


    default VariantEntity map(String name) {
        VariantEntity variantEntity = new VariantEntity();
        variantEntity.setName(name);
        return variantEntity;
    }

    default String map(VariantEntity value) {
        return value.getName();
    }

}
