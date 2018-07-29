package springg.boot.angjs.service;

import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.model.RentPoint;

public interface RentService {
    void rentCar(History history);

    void returnCar(History history);

    public float getAverageTime(String address, String carName);

    public Car getCurrentCar(String number);

    public Car getCurrentCar(Long id);

    void setCarList(Car car);

    void deleteCarFromCarList(String address, Long id);

    boolean checkRentPoint(String address);

    public void updateRentPointAddressForCar(RentPoint point, String oldAddress);

    public void deleteRentPointAddressForCar(RentPoint point);

    public void updateHistoryList(Car car);
}
