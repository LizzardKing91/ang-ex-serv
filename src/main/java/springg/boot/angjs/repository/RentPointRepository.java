package springg.boot.angjs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springg.boot.angjs.model.RentPoint;

public interface RentPointRepository extends JpaRepository<RentPoint, Long> {
}
