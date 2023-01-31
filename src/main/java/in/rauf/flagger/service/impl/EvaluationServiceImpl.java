package in.rauf.flagger.service.impl;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.SegmentEntity;
import in.rauf.flagger.model.DistributionContext;
import in.rauf.flagger.model.evaluation.EvaluationRequest;
import in.rauf.flagger.model.evaluation.EvaluationResult;
import in.rauf.flagger.repo.FlagRepository;
import in.rauf.flagger.service.DistributionService;
import in.rauf.flagger.service.EvaluationService;
import in.rauf.flagger.service.RuleEvaluatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    public static final String DEFAULT_VARIANT = "control";

    private final FlagRepository flagRepository;
    private final RuleEvaluatorService ruleEvaluatorService;
    private final DistributionService distributionService;

    public EvaluationServiceImpl(FlagRepository flagRepository, RuleEvaluatorService ruleEvaluatorService, DistributionService distributionService) {
        this.flagRepository = flagRepository;
        this.ruleEvaluatorService = ruleEvaluatorService;
        this.distributionService = distributionService;
    }

    @Override
    public EvaluationResult evaluate(EvaluationRequest request) {
        if (request == null) {
            return defaultResult(null, null, "null request");
        }

        var flagOpt = flagRepository.findByName(request.flagName());
        if (flagOpt.isEmpty()) {
            log.info("flag: {} does not exist", request.flagName());
            return defaultResult(null, request.context(), String.format("flag: %s does not exist", request.flagName()));
        }
        var flagEntity = flagOpt.get();

        if (!flagEntity.getEnabled()) {
            log.info("flag: {} is not enabled", request.flagName());
            return defaultResult(null, request.context(), String.format("flag: %s is not enabled", request.flagName()));
        }
        if (flagEntity.getSegments().isEmpty()) {
            log.info("flag:{} does not have any segments", request.flagName());
            return defaultResult(null, request.context(), String.format("flag: %s does not have any segments", request.flagName()));
        }
        var req = request.id() == null ? request.withId(UUID.randomUUID().toString()) : request;

        var segments = flagEntity.getSegments().stream().sorted(Comparator.comparingInt(SegmentEntity::getPriority)).toList();

        for (var segment : segments) {
            var distributionContext = DistributionContext.from(segment);
            var variantOpt = evaluateSegment(distributionContext, req, segment);

            if (variantOpt.isPresent()) {
                log.info("segment with id: {} and name: {} matched, returning variant: {}", segment.getId(), segment.getName(), variantOpt.get());
                return result(flagEntity, req.context(), variantOpt.get());
            }
        }
        return defaultResult(flagEntity, req.context(), "no rollout ");
    }

    private Optional<String> evaluateSegment(DistributionContext distributionContext, EvaluationRequest request, SegmentEntity segment) {
        if (segment.getConstraint() != null) {
            boolean matched = ruleEvaluatorService.evaluate(segment.getConstraint(), request.context());
            if (!matched) {
                return Optional.empty();
            }
        }
        return distributionService.getVariant(distributionContext, request.id(), segment.getRolloutPercentage());
    }

    private EvaluationResult result(FlagEntity flagEntity, Object context, String variantName) {
        var flagId = flagEntity == null ? null : flagEntity.getId();
        var flagName = flagEntity == null ? null : flagEntity.getName();

        return new EvaluationResult(context, flagId, flagName, variantName, null);
    }

    private EvaluationResult defaultResult(FlagEntity flagEntity, Object context, String msg) {
        var flagId = flagEntity == null ? null : flagEntity.getId();
        var flagName = flagEntity == null ? null : flagEntity.getName();

        return new EvaluationResult(context, flagId, flagName, DEFAULT_VARIANT, msg);
    }


}
