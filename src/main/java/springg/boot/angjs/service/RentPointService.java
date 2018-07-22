package springg.boot.angjs.service;

import springg.boot.angjs.model.RentPoint;

import java.util.List;
import java.util.Optional;

public interface RentPointService {

    List<RentPoint> getAllRentPoints();

    Optional<RentPoint> getRentPoint(Long id);

    void deleteRentPoint(Long id);

    void createRentPoint(RentPoint rentPoint);

}
