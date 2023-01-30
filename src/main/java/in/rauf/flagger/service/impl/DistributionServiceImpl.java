package in.rauf.flagger.service.impl;

import in.rauf.flagger.model.DistributionContext;
import in.rauf.flagger.service.DistributionService;
import in.rauf.flagger.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static in.rauf.flagger.service.impl.EvaluationServiceImpl.DEFAULT_VARIANT;

@Service
public class DistributionServiceImpl implements DistributionService {

    private static final Logger log = LoggerFactory.getLogger(DistributionServiceImpl.class);
    public final static int BUCKET_SIZE = 10000;
    public final static int MULTIPLIER = BUCKET_SIZE / 100;

    @Override
    public Optional<String> getVariant(DistributionContext context, String id, Integer rolloutPercent) {
        if (id == null || rolloutPercent == null) {
            log.info("null id or rollout percent");
            return Optional.empty();
        }
        if (rolloutPercent == 0) {
            log.info("0% rollout percentage");
            return Optional.empty();
        }
        var crc = HashUtils.getCRC32(id.getBytes());
        var bucketNum = crc % BUCKET_SIZE;
        int variantIndex = getVariantIndex(context, (int) bucketNum + 1);
        if (variantIndex >= context.cumulativePercentage().size()) {
            return Optional.of(DEFAULT_VARIANT);
        }

        var variantName = context.variantNames().get(variantIndex);
        if (rolloutPercent == 100) {
            return Optional.of(variantName);
        }
        if (checkRollout(context, rolloutPercent, bucketNum, variantIndex)) {
            return Optional.of(variantName);
        }
        return Optional.empty();
    }

    private boolean checkRollout(DistributionContext context, Integer rolloutPercent, long bucketNum, int variantIndex) {
        var max = context.cumulativePercentage().get(variantIndex);
        var min = variantIndex == 0 ? 0 : context.cumulativePercentage().get(variantIndex - 1);
        var roll = Math.max(max - min, 0);
        return 100 * (bucketNum - min) <= (long) (roll) * rolloutPercent;
    }

    private static int getVariantIndex(DistributionContext context, int bucketNum) {
        var size = context.cumulativePercentage().size();
        for (int i = 0; i < size; i++) {
            if (bucketNum < context.cumulativePercentage().get(i)) {
                return i;
            }
        }
        return size;
    }
}
