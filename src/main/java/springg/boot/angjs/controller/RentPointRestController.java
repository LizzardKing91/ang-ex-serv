package springg.boot.angjs.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.service.RentPointService;
import springg.boot.angjs.service.RentService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
public class RentPointRestController {

    private RentPointService rentPointService;
    private RentService rentService;

    @Autowired
    public RentPointRestController(RentPointService rentPointService, RentService rentService){
        this.rentPointService = rentPointService;
        this.rentService = rentService;
    }

    @GetMapping("/points")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<RentPoint> getAllPoints(){
        return rentPointService.getAllRentPoints();
    }

    @GetMapping("/points/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public RentPoint getPointById(@PathVariable Long id) throws NotFoundException {
        Optional<RentPoint> point = rentPointService.getRentPoint(id);

        if (!point.isPresent())
            throw new NotFoundException("id-" + id);

        return point.get();
    }

    @PostMapping("/points")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> createPoint(@RequestBody RentPoint point) {
        rentPointService.createRentPoint(point);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(point.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/points/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> updateRentPoint(@RequestBody RentPoint newPoint, @PathVariable long id) {
        Optional<RentPoint> oldPoint = rentPointService.getRentPoint(id);

        if(!oldPoint.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        newPoint.setId(id);
        newPoint.setCarList(oldPoint.get().getCarList());

        String oldAddress = oldPoint.get().getAddress();

        rentService.updateRentPointAddressForCar(newPoint, oldAddress);

        rentPointService.createRentPoint(newPoint);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/points/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> deletePoint(@PathVariable long id) {
        if(!rentPointService.getRentPoint(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        rentService.deleteRentPointAddressForCar(rentPointService.getRentPoint(id).get());
        rentPointService.deleteRentPoint(id);

        return ResponseEntity.noContent().build();
    }
}
