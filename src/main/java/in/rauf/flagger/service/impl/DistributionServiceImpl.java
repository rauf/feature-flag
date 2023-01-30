package in.rauf.flagger.service.impl;

import in.rauf.flagger.model.DistributionContext;
import in.rauf.flagger.service.DistributionService;
import in.rauf.flagger.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

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
        int variantIndex = getVariantIndex(context, (int) bucketNum);
        var variantName = context.variantNames().get(variantIndex);
        if (rolloutPercent == 100) {
            return Optional.of(variantName);
        }
        boolean res = checkRollout(context, rolloutPercent, bucketNum, variantIndex);
        if (res) {
            return Optional.of(variantName);
        }
        return Optional.empty();
    }

    private boolean checkRollout(DistributionContext distributionContext, Integer rolloutPercent, long bucketNum, int variantIndex) {
        var min = 0;
        var max = distributionContext.cumulativePercentage().get(variantIndex);
        var roll = 0;
        if (variantIndex != 0) {
            min = distributionContext.cumulativePercentage().get(variantIndex - 1);
        }
        if (max - min - 1 > 0) {
            roll = max - min - 1;
        }
        return 100 * (bucketNum - min) <= (long) (roll) * rolloutPercent;
    }

    private static int getVariantIndex(DistributionContext distributionContext, int bucketNum) {
        var variantIndex = Collections.binarySearch(distributionContext.cumulativePercentage(), bucketNum);
        return variantIndex < 0 ? -variantIndex + 1 : variantIndex;
    }
}
