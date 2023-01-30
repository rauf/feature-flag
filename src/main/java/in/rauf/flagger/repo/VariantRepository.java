package in.rauf.flagger.repo;

import in.rauf.flagger.entities.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VariantRepository extends JpaRepository<VariantEntity, Long> {

    Optional<VariantEntity> findByName(String name);
}
