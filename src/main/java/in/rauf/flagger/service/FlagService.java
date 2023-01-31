package in.rauf.flagger.service;

import in.rauf.flagger.model.dto.SaveFlagRequestDTO;
import in.rauf.flagger.model.dto.SaveFlagResponseDTO;

public interface FlagService {

    SaveFlagResponseDTO save(SaveFlagRequestDTO saveFlagRequestDTO);

}
