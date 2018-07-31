package springg.boot.angjs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springg.boot.angjs.model.History;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> getHistoriesByCarNumber(String carNumber);
}
