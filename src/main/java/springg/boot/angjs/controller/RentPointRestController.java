package springg.boot.angjs.controller;

import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.service.RentPointService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
public class RentPointRestController {

    private RentPointService rentPointService;

    public RentPointRestController(RentPointService rentPointService){
        this.rentPointService = rentPointService;
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
    public ResponseEntity<Object> updateRentPoint(@RequestBody RentPoint point, @PathVariable long id) {
        Optional<RentPoint> rentPoint = rentPointService.getRentPoint(id);

        if(!rentPoint.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        point.setId(id);

        rentPointService.createRentPoint(point);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/points/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deletePoint(@PathVariable long id) {
        rentPointService.deleteRentPoint(id);
    }
}
