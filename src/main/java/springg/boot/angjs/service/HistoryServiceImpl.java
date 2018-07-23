package springg.boot.angjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.repository.CarRepository;
import springg.boot.angjs.repository.HistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService {

    private HistoryRepository repository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<History> getAllHistory() {
        return repository.findAll();
    }

    @Override
    public Optional<History> getHistory(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteHistory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public History createHistory(History history) {
        repository.save(history);

        return history;
    }
}
