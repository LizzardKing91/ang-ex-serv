package springg.boot.angjs.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.service.RentPointService;

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
    public RentPoint getPointById(Long id){
        Optional<RentPoint> point = studentRepository.findById(id);

        if (!student.isPresent())
            throw new StudentNotFoundException("id-" + id);

        return student.get();
    }
}
