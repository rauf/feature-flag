package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.VariantEntity;
import in.rauf.flagger.model.dto.FetchFlagsWithSegmentsResponseDTO;
import in.rauf.flagger.model.dto.SaveFlagRequestDTO;
import in.rauf.flagger.model.dto.SaveFlagResponseDTO;
import in.rauf.flagger.model.dto.VariantDTO;
import in.rauf.flagger.model.errors.BadRequestException;
import in.rauf.flagger.model.mapper.FlagMapper;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.service.FlagService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class FlagServiceImpl implements FlagService {
    private final FlagRepository flagRepository;
    private final FlagMapper flagMapper;

    public FlagServiceImpl(FlagRepository flagRepository, FlagMapper flagMapper) {
        this.flagRepository = flagRepository;
        this.flagMapper = flagMapper;
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

    @Override
    public FetchFlagsWithSegmentsResponseDTO findAllWithSegments() {
        var flags = flagRepository.findAll();
        var dtos = flagMapper.toDto(flags);
        return new FetchFlagsWithSegmentsResponseDTO(dtos);
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
