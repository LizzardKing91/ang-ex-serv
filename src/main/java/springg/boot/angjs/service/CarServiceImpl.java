package springg.boot.angjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getAvailableCars() {
        return carRepository.findAll().stream().filter(Car::isAvailable).collect(Collectors.toList());
    }

    @Override
    public boolean isCarExist(Long id) {
        return carRepository.existsById(id);
    }

    public boolean isCarExist(Car car) throws Exception {
        try {
            if(car.getId() != null){
                return true;
            }
            return car.getNumber() != null;
        }
        catch (IllegalArgumentException ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public void create(Car car) {
        carRepository.save(car);
    }

    @Override
    public Car getCarByNumber(String number) {
        return carRepository.getCarByNumber(number);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
