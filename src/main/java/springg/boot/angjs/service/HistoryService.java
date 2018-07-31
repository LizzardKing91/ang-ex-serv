package springg.boot.angjs.service;

import springg.boot.angjs.model.History;
import springg.boot.angjs.model.RentPoint;

import java.util.List;
import java.util.Optional;

public interface HistoryService {
    List<History> getAllHistory();

    Optional<History> getHistory(Long id);

    void deleteHistory(Long id);

    History createHistory(History history);

    List<History> getHistoriesByCarNumber(String carNumber);
}
