package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.DistributionEntity;
import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.SegmentEntity;
import in.rauf.flagger.model.dto.CreateSegmentRequest;
import in.rauf.flagger.model.dto.CreateSegmentResponse;
import in.rauf.flagger.model.dto.SegmentDTO;
import in.rauf.flagger.model.errors.BadRequestException;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.repo.SegmentRepository;
import in.rauf.flagger.repo.VariantRepository;
import in.rauf.flagger.service.SegmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Transactional
    public CreateSegmentResponse save(String flagName, CreateSegmentRequest request) {
        var flagOpt = flagRepository.findByName(flagName);
        if (flagOpt.isEmpty()) {
            throw new BadRequestException(String.format("flag with name: %s not present", flagName));
        }
        if (!checkPercentSum(request.getSegments())) {
            throw new BadRequestException("distribution sum > 100");
        }
        segmentRepository.deleteByFlag(flagOpt.get()); // delete existing segments for the flag (if any)

        var segmentList = request.getSegments().stream().map(s -> getSegmentEntity(flagOpt.get(), s)).toList();
        segmentRepository.saveAll(segmentList);
        return new CreateSegmentResponse(request.getSegments());
    }

    private boolean checkPercentSum(List<SegmentDTO> segments) {
        for (var segment : segments) {
            int sum = 0;
            for (var dist : segment.getDistributions()) {
                sum += dist.getPercent();
            }
            if (sum > 100) {
                return false;
            }
        }
        return true;
    }

    public SegmentEntity getSegmentEntity(FlagEntity flagEntity, SegmentDTO request) {
        var segmentEntity = new SegmentEntity(request.getName(), request.getPriority(), request.getRolloutPercentage(),
                flagEntity, request.getConstraint(), null);

        segmentEntity.setDistributions(getDistributionEntities(flagEntity, request, segmentEntity));
        return segmentEntity;
    }

    private Set<DistributionEntity> getDistributionEntities(FlagEntity flagEntity, SegmentDTO request, SegmentEntity segmentEntity) {
        return request.getDistributions().stream().map(d -> {
            var variantOpt = variantRepository.findByName(d.getVariant());
            if (variantOpt.isEmpty()) {
                throw new BadRequestException(String.format("variant with name: %s not present", d.getVariant()));
            }
            return new DistributionEntity(d.getName(), d.getPercent(), flagEntity, segmentEntity, variantOpt.get());
        }).collect(Collectors.toSet());
    }

}
