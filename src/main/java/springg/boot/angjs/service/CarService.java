package springg.boot.angjs.service;

import springg.boot.angjs.model.Car;

import java.util.List;
import java.util.Optional;


public interface CarService {

    public List<Car> getAllCars();

    public List<Car> getAvailableCars();

    public Optional<Car> getCarById(Long id);

    public void create(Car car);

    public Car getCarByNumber(String number);

    public void deleteCar(Long id);
}
