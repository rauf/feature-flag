package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.VariantEntity;
import in.rauf.flagger.model.dto.*;
import in.rauf.flagger.model.errors.BadRequestException;
import in.rauf.flagger.model.mapper.FlagMapper;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.service.FlagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class FlagServiceImpl implements FlagService {

    private static final Logger log = LoggerFactory.getLogger(FlagServiceImpl.class);
    private final FlagRepository flagRepository;
    private final FlagMapper flagMapper;

    public FlagServiceImpl(FlagRepository flagRepository, FlagMapper flagMapper) {
        this.flagRepository = flagRepository;
        this.flagMapper = flagMapper;
    }

    @Override
    public SaveFlagResponseDTO save(FlagRequestDTO flagRequestDTO) {
        if (flagRepository.findByName(flagRequestDTO.getName()).isPresent()) {
            throw new BadRequestException(String.format("flag: %s already present", flagRequestDTO.getName()));
        }
        FlagEntity flagEntity = getEntity(flagRequestDTO);
        var persistedFlagEntity = flagRepository.save(flagEntity);
        return toDto(persistedFlagEntity);
    }

    @Override
    public FetchFlagsWithSegmentsResponseDTO findAllWithSegments() {
        var flags = flagRepository.findAll();
        var dtos = flagMapper.toDto(flags);
        return new FetchFlagsWithSegmentsResponseDTO(dtos);
    }

    @Override
    public FlagDTO update(String name, FlagRequestDTO flagDTO) {
        log.debug("Request to save Flag : {}", flagDTO);

        var flagEntityOpt = flagRepository.findByName(name);
        if (flagEntityOpt.isEmpty()) {
            throw new BadRequestException(String.format("flag: %s not present", name));
        }
        var flagEntity = flagEntityOpt.get();
        flagEntity.setDescription(flagDTO.getDescription());
        flagEntity.setEnabled(flagDTO.getEnabled());
        var persisted = flagRepository.save(flagEntity);
        return flagMapper.toDto(persisted);
    }

    @Override
    public FlagDTO getFlag(String name) {
        var flagEntityOpt = flagRepository.findByName(name);
        if (flagEntityOpt.isEmpty()) {
            throw new BadRequestException(String.format("flag: %s not present", name));
        }
        return flagMapper.toDto(flagEntityOpt.get());
    }

    private FlagEntity getEntity(FlagRequestDTO dto) {
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
