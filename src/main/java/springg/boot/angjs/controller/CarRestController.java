package springg.boot.angjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.service.CarService;
import springg.boot.angjs.service.CarServiceImpl;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class CarRestController {

    private CarService carService;

    @Autowired
    public CarRestController(CarServiceImpl carService){
        this.carService = carService;
    }

    @GetMapping("/car/list")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAllCars(){
        return carService.getAllCars();
    }

    @GetMapping("/car/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Car getCarById(@PathVariable Long id){
        if(carService.getCarById(id).isPresent()){
            return carService.getCarById(id).get();
        }
        return new Car();
    }

    @GetMapping("/car/")
    @CrossOrigin(origins = "http://localhost:4200")
    public Car getCarById(@RequestParam(value="number") String number){
        if(carService.getCarByNumber(number).getId() != null)
            return carService.getCarByNumber(number);
        return new Car();
    }

    @GetMapping("car/available")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAvailableCars(){
        return carService.getAvailableCars();
    }

    @GetMapping("car/available/{rentPointAddress}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Car> getAvailableCarsAtCurrentPoint(@PathVariable("rentPointAddress") String rentPointAddress){
        return carService.getAvailableCars()
                .stream().filter(car -> car.getCurrentPoint().
                        equals(rentPointAddress)).collect(Collectors.toList());
    }

    /*@GetMapping("car/available/{rentPointId}")
    public Collection<Car> getAvailableCarsAtCurrentPointId(@PathVariable("rentPointId") String rentPointId){
        return carService.getAvailableCars()
                .stream().filter(car -> car.getCurrentPoint().
                        equals(rentPointId)).collect(Collectors.toList());
    }*/

    @PostMapping("/car/add/")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> addCar(@RequestBody Car car){

        if(car.getName() != null && car.getNumber() != null && (!carService.isCarExist(car.getId()))){
            carService.saveOrUpdate(car);

            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return  ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @PutMapping("/car/edit")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> editCar(@RequestBody Car car){
        if(carService.isCarExist(car.getId()))
            carService.saveOrUpdate(car);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/car/delete")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> deleteCar(@RequestBody Car car){
        if(carService.getCarById(car.getId()).isPresent()){
            carService.deleteCar(car.getId());

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
    }

}
