package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.VariantEntity;
import in.rauf.flagger.model.dto.FlagDTO;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.service.FlagService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class FlagServiceImpl implements FlagService {
    private final FlagRepository flagRepository;

    public FlagServiceImpl(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    @Override
    public FlagDTO save(FlagDTO flagDTO) {
        FlagEntity flagEntity = getEntity(flagDTO);
        var persistedFlagEntity = flagRepository.save(flagEntity);

        return null;
    }

    private FlagEntity getEntity(FlagDTO dto) {
        var flagEntity = new FlagEntity();
        flagEntity.setName(dto.getName());
        flagEntity.setDescription(dto.getDescription());
        flagEntity.setEnabled(dto.getEnabled());

        var variantEntities = dto.getVariants().stream().map(v -> new VariantEntity(v, flagEntity)).collect(Collectors.toSet());
        flagEntity.setVariants(variantEntities);
        return flagEntity;
    }

}
