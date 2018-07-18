package springg.boot.angjs.service;

import org.springframework.stereotype.Service;
import springg.boot.angjs.model.Car;

import java.util.List;
import java.util.Optional;


public interface CarService {

    public List<Car> getAllCars();

    public List<Car> getAvailableCars();

    public boolean isCarExist(Long id);

    public Optional<Car> getCarById(Long id);

    public void saveOrUpdate(Car car);

    public Car getCarByNumber(String number);

    public void deleteCar(Long id);
}
