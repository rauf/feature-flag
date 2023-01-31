package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.VariantEntity;
import in.rauf.flagger.model.dto.SaveFlagRequestDTO;
import in.rauf.flagger.model.dto.SaveFlagResponseDTO;
import in.rauf.flagger.model.dto.VariantDTO;
import in.rauf.flagger.model.errors.BadRequestException;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.service.FlagService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class FlagServiceImpl implements FlagService {
    private final FlagRepository flagRepository;

    public FlagServiceImpl(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    @Override
    public SaveFlagResponseDTO save(SaveFlagRequestDTO saveFlagRequestDTO) {
        if (flagRepository.findByName(saveFlagRequestDTO.getName()).isPresent()) {
            throw new BadRequestException(String.format("flag: %s already present", saveFlagRequestDTO.getName()));
        }
        FlagEntity flagEntity = getEntity(saveFlagRequestDTO);
        var persistedFlagEntity = flagRepository.save(flagEntity);
        return toDto(persistedFlagEntity);
    }

    private FlagEntity getEntity(SaveFlagRequestDTO dto) {
        var flagEntity = new FlagEntity();
        flagEntity.setName(dto.getName());
        flagEntity.setDescription(dto.getDescription());
        flagEntity.setEnabled(dto.getEnabled());

        var variantEntities = dto.getVariants().stream().map(v -> new VariantEntity(v, flagEntity)).collect(Collectors.toSet());
        flagEntity.setVariants(variantEntities);
        return flagEntity;
    }

    private SaveFlagResponseDTO toDto(FlagEntity flagEntity) {

        var variants = new HashSet<VariantDTO>();
        for (var v : flagEntity.getVariants()) {
            variants.add(new VariantDTO(v.getName()));
        }
        return new SaveFlagResponseDTO(
                flagEntity.getId(), flagEntity.getName(), flagEntity.getDescription(), flagEntity.getEnabled(), variants
        );
    }


}
