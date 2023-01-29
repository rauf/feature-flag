package in.rauf.flagger.model;

import in.rauf.flagger.entities.SegmentEntity;

import java.util.ArrayList;
import java.util.List;

import static in.rauf.flagger.service.impl.DistributionServiceImpl.MULTIPLIER;

public record DistributionContext(List<String> variantNames, List<Integer> cumulativePercentage) {

    public static DistributionContext from(SegmentEntity segment) {
        List<String> variantNames = new ArrayList<>(segment.getDistributions().size());
        List<Integer> cumulativePercentages = new ArrayList<>(segment.getDistributions().size());
        int lastPercent = 0;

        for (var dist : segment.getDistributions()) {
            variantNames.add(dist.getVariant().getName());
            cumulativePercentages.add(lastPercent + (dist.getPercent() * MULTIPLIER));
            lastPercent = cumulativePercentages.get(cumulativePercentages.size() - 1);
        }

        return new DistributionContext(variantNames, cumulativePercentages);
    }


}
