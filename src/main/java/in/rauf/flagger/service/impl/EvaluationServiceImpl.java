package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.SegmentEntity;
import in.rauf.flagger.model.DistributionContext;
import in.rauf.flagger.model.EvaluationRequest;
import in.rauf.flagger.model.EvaluationResult;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.repo.VariantRepository;
import in.rauf.flagger.service.DistributionService;
import in.rauf.flagger.service.EvaluationService;
import in.rauf.flagger.service.RuleEvaluatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EvaluationServiceImpl implements EvaluationService {

    public static final String DEFAULT_VARIANT = "control";

    private final FlagRepository flagRepository;
    private final RuleEvaluatorService ruleEvaluatorService;
    private final DistributionService distributionService;

    public EvaluationServiceImpl(FlagRepository flagRepository, VariantRepository variantRepository, RuleEvaluatorService ruleEvaluatorService, DistributionService distributionService) {
        this.flagRepository = flagRepository;
        this.ruleEvaluatorService = ruleEvaluatorService;
        this.distributionService = distributionService;
    }

    @Override
    public EvaluationResult evaluate(EvaluationRequest request) {
        if (request == null) {
            return defaultResult(null, null, "request is blank");
        }

        var flagOpt = flagRepository.findByName(request.getFlagName());
        if (flagOpt.isEmpty()) {
            log.info("flag: {} does not exist", request.getFlagName());
            return defaultResult(null, request.getContext(), "flag does not exists: " + request.getFlagName());
        }
        var flagEntity = flagOpt.get();

        if (!flagEntity.getEnabled()) {
            log.info("flag: {} is not enabled", request.getFlagName());
            return defaultResult(null, request.getContext(), "flag is not enabled: " + request.getFlagName());
        }
        if (flagEntity.getSegments().isEmpty()) {
            log.info("flag:{} does not have any segments", request.getFlagName());
            return defaultResult(null, request.getContext(), "flag does not have any segments: " + request.getFlagName());
        }
        if (request.getId() == null) {
            request.setId(UUID.randomUUID().toString());
        }

        for (var segment : flagEntity.getSegments()) {
            var distributionContext = DistributionContext.from(segment);
            var variantOpt = evaluateSegment(distributionContext, request, segment);

            if (variantOpt.isPresent()) {
                log.info("segment with id: {} and name: {} matched, returning variant: {}", segment.getId(), segment.getName(), variantOpt.get());
                result(flagEntity, request.getContext(), variantOpt.get(), "variant received");
            }
        }
        return defaultResult(flagEntity, request.getContext(), "no rollout ");
    }

    private Optional<String> evaluateSegment(DistributionContext distributionContext, EvaluationRequest request, SegmentEntity segment) {
        if (segment.getConstraint() != null) {
            boolean matched = ruleEvaluatorService.evaluate(segment.getConstraint().getValue(), request.getContext());
            if (!matched) {
                return Optional.empty();
            }
        }
        return distributionService.getVariant(distributionContext, request.getId(), segment.getRolloutPercentage());
    }

    private EvaluationResult result(FlagEntity flagEntity, Object context, String variantName, String msg) {
        var flagId = flagEntity == null ? null : flagEntity.getId();
        var flagName = flagEntity == null ? null : flagEntity.getName();

        return new EvaluationResult(context, flagId, flagName, variantName, msg);
    }

    private EvaluationResult defaultResult(FlagEntity flagEntity, Object context, String msg) {
        var flagId = flagEntity == null ? null : flagEntity.getId();
        var flagName = flagEntity == null ? null : flagEntity.getName();

        return new EvaluationResult(context, flagId, flagName, DEFAULT_VARIANT, msg);
    }


}
