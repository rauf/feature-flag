package in.rauf.flagger.service;

import in.rauf.flagger.model.dto.CreateSegmentRequest;
import in.rauf.flagger.model.dto.CreateSegmentResponse;
import in.rauf.flagger.model.dto.GetSegmentsResponse;

public interface SegmentService {
    CreateSegmentResponse save(String flagName, CreateSegmentRequest request);

    GetSegmentsResponse findAll(String flagName);
}
