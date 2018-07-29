package springg.boot.angjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.service.CarService;
import springg.boot.angjs.service.CarServiceImpl;
import springg.boot.angjs.service.RentService;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CarRestController {

    private CarService carService;
    private RentService rentService;

    @Autowired
    public CarRestController(CarServiceImpl carService, RentService rentService){
        this.carService = carService;
        this.rentService = rentService;
    }

    @GetMapping("/cars")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Car getCarById(@PathVariable Long id){
        if(carService.getCarById(id).isPresent()){
            return carService.getCarById(id).get();
        }
        return new Car();
    }

    @GetMapping("cars/available")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAvailableCars(){
        return carService.getAvailableCars();
    }

    @GetMapping("cars/available/{rentPointAddress}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAvailableCarsAtCurrentPoint(@PathVariable("rentPointAddress") String rentPointAddress){
        if(rentPointAddress != null){
            return carService.getAvailableCars()
                    .stream().filter(car ->  car.getCurrentPoint()
                            .equals(rentPointAddress))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @PostMapping("/cars")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> createCar(@RequestBody Car car) {
        if(carService.getCarByNumber(car.getNumber()) != null) {
            return ResponseEntity.badRequest().build();
        }

        carService.create(car);

        rentService.setCarList(car);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(car.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> updateCar(@RequestBody Car newCar, @PathVariable long id) {

        Optional<Car> oldCar = carService.getCarById(id);

        if (!oldCar.isPresent())
            return ResponseEntity.notFound().build();

        String oldAddress = oldCar.get().getCurrentPoint();

        if(newCar.getName() != null) {
            oldCar.get().setName(newCar.getName());
        }

        if(newCar.getNumber() != null) {
            oldCar.get().setNumber(newCar.getNumber());
        }

        if((newCar.getCurrentPoint() != null && rentService.checkRentPoint(newCar.getCurrentPoint()))) {
            oldCar.get().setCurrentPoint(newCar.getCurrentPoint());
        }
        if(newCar.getCurrentPoint() != null && !newCar.getCurrentPoint().equals(oldAddress)) {
            rentService.deleteCarFromCarList(oldAddress, oldCar.get().getId());
            rentService.setCarList(oldCar.get());
        }

        carService.create(oldCar.get());
        rentService.updateHistoryList(oldCar.get());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteCar(@PathVariable long id) {
        rentService.deleteCarFromCarList(carService.getCarById(id).get().getCurrentPoint(), id);
        carService.deleteCar(id);
    }
}
