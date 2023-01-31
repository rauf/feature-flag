package in.rauf.flagger.model;

import in.rauf.flagger.entities.DistributionEntity;
import in.rauf.flagger.entities.SegmentEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static in.rauf.flagger.service.impl.DistributionServiceImpl.MULTIPLIER;

public record DistributionContext(List<String> variantNames, List<Integer> cumulativePercentage) {

    public static DistributionContext from(SegmentEntity segment) {
        var distributions = segment.getDistributions().stream().sorted(Comparator.comparingLong(DistributionEntity::getId)).toList();
        List<String> variantNames = new ArrayList<>(distributions.size());
        List<Integer> cumulativePercentages = new ArrayList<>(distributions.size());
        int lastPercent = 0;

        for (var dist : distributions) {
            variantNames.add(dist.getVariant().getName());
            var percent = lastPercent + (dist.getPercent() * MULTIPLIER);
            cumulativePercentages.add(percent);
            lastPercent = percent;
        }

        return new DistributionContext(variantNames, cumulativePercentages);
    }


}
