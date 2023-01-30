package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.DistributionEntity;
import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.SegmentEntity;
import in.rauf.flagger.model.dto.CreateSegmentRequest;
import in.rauf.flagger.model.dto.SegmentDTO;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.repo.SegmentRepository;
import in.rauf.flagger.repo.VariantRepository;
import in.rauf.flagger.service.SegmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SegmentServiceImpl implements SegmentService {

    private final SegmentRepository segmentRepository;
    private final FlagRepository flagRepository;
    private final VariantRepository variantRepository;

    public SegmentServiceImpl(SegmentRepository segmentRepository, FlagRepository flagRepository, VariantRepository variantRepository) {
        this.segmentRepository = segmentRepository;
        this.flagRepository = flagRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public CreateSegmentRequest save(String flagName, CreateSegmentRequest request) {
        var flagOpt = flagRepository.findByName(flagName);
        if (flagOpt.isEmpty()) {
            throw new RuntimeException("flag does not exist"); // TODO: exception handling, return 400
        }
        var segmentList = new ArrayList<SegmentEntity>();
        for (var seg : request.getSegments()) {
            segmentList.add(getEntity(flagOpt.get(), seg));
        }

        var persisted = segmentRepository.saveAll(segmentList);
        return null;
    }

    public SegmentEntity getEntity(FlagEntity flagEntity, SegmentDTO request) {

        var segmentEntity = new SegmentEntity();
        segmentEntity.setFlag(flagEntity);
        segmentEntity.setName(request.getName());
        segmentEntity.setPriority(request.getPriority());
        segmentEntity.setRolloutPercentage(request.getRolloutPercentage());
        segmentEntity.setConstraint(request.getConstraint());

        var distributionEntities = getDistributionEntities(flagEntity, request, segmentEntity);
        segmentEntity.setDistributions(distributionEntities);
        return segmentEntity;
    }

    private Set<DistributionEntity> getDistributionEntities(FlagEntity flagEntity, SegmentDTO request, SegmentEntity segmentEntity) {
        return request.getDistributions().stream().map(d -> {
            var de = new DistributionEntity();
            de.setSegment(segmentEntity);
            de.setFlag(flagEntity);
            de.setName(d.getName());
            de.setPercent(d.getPercent());

            var variantOpt = variantRepository.findByName(d.getVariant());
            if (variantOpt.isEmpty()) {
                throw new RuntimeException("variant not present: " + d.getVariant());
            }
            de.setVariant(variantOpt.get());
            return de;
        }).collect(Collectors.toSet());
    }

}
