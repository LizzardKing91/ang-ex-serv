package springg.boot.angjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.repository.RentPointRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RentPointServiceImpl implements RentPointService{

    private RentPointRepository repository;

    @Autowired
    public RentPointServiceImpl(RentPointRepository repository){
        this.repository = repository;
    }

    @Override
    public List<RentPoint> getAllRentPoints() {
        return repository.findAll();
    }

    @Override
    public Optional<RentPoint> getRentPoint(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteRentPoint(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void createRentPoint(RentPoint rentPoint) {
        repository.save(rentPoint);
    }

    @Override
    public RentPoint getRentPointByAddress(String address) {
        return repository.getRentPointByAddress(address);
    }
}
