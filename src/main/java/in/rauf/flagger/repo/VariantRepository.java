package in.rauf.flagger.repo;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.VariantEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {

    Optional<VariantEntity> findByFlagAndName(FlagEntity flag, @NotNull String name);
}
