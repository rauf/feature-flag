package in.rauf.flagger.service;

import in.rauf.flagger.model.dto.CreateSegmentRequest;

public interface SegmentService {
    CreateSegmentRequest save(String flagName, CreateSegmentRequest request);
}
