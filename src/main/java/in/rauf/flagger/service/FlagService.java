package in.rauf.flagger.service;

import in.rauf.flagger.model.dto.FetchFlagsWithSegmentsResponseDTO;
import in.rauf.flagger.model.dto.FlagDTO;
import in.rauf.flagger.model.dto.FlagRequestDTO;
import in.rauf.flagger.model.dto.SaveFlagResponseDTO;

public interface FlagService {

    SaveFlagResponseDTO save(FlagRequestDTO flagRequestDTO);

    FetchFlagsWithSegmentsResponseDTO findAllWithSegments();

    FlagDTO update(String name, FlagRequestDTO flagDTO);
}
