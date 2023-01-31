package in.rauf.flagger.repo;

import in.rauf.flagger.entities.FlagEntity;
import in.rauf.flagger.entities.SegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<SegmentEntity, Long> {
    void deleteByFlag(FlagEntity flagEntity);
}
