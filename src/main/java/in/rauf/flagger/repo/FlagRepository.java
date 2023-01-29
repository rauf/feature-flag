package in.rauf.flagger.repo;

import in.rauf.flagger.entities.FlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlagRepository extends JpaRepository<FlagEntity, Long> {

    Optional<FlagEntity> findByName(String name);

}
