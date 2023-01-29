package in.rauf.flagger.service;

import in.rauf.flagger.model.DistributionContext;

import java.util.Optional;

public interface DistributionService {

    Optional<String> getVariant(DistributionContext distributionContext, String id, Integer rolloutPercent);
}
