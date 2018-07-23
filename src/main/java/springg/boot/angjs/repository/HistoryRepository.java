package springg.boot.angjs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springg.boot.angjs.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
