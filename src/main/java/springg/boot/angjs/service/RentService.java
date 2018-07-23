package springg.boot.angjs.service;

import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;

public interface RentService {
    void rentCar(History history);

    void returnCar(History history, String rentPointAddress);

    public float getAverageTime(String address, String carName);

    public Car getCurrentCar(String number);
}
