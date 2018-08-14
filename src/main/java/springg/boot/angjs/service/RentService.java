package springg.boot.angjs.service;

import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.model.RentPoint;

import java.util.List;

public interface RentService {
    void rentCar(History history);

    void returnCar(History history);

    double getAverageTime(String address, String carName);

    List getAverageTimeList();

    Car getCurrentCar(String number);

    void setCarList(Car car);

    void deleteCarFromCarList(String address, Long id);

    boolean checkRentPoint(String address);

    void updateRentPointAddressForCar(RentPoint point, String oldAddress);

    void deleteRentPointAddressForCar(RentPoint point);

    void updateHistoryList(Car car);
}
