package springg.boot.angjs.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.service.HistoryService;
import springg.boot.angjs.service.RentService;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RestController
public class HistoryRestController {

    private HistoryService historyService;
    private RentService rentService;

    @Autowired
    public HistoryRestController(HistoryService historyService, RentService rentService){
        this.historyService = historyService;
        this.rentService = rentService;
    }

    @GetMapping("/history")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<History> getAllHistory(){
        return historyService.getAllHistory();
    }

    @GetMapping("/history/{address}/{carName}")
    @CrossOrigin(origins = "http://localhost:4200")
    public float getAverageRentTimeForPointAndModel(@PathVariable String address, @PathVariable String carName){
        return rentService.getAverageTime(address, carName);
    }

    @GetMapping("/history/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public History getHistoryById(@PathVariable Long id) throws NotFoundException {
        Optional<History> history = historyService.getHistory(id);

        if (!history.isPresent())
            throw new NotFoundException("id-" + id);

        return history.get();
    }

    @PostMapping("/cars/rent")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> rentCar(@RequestBody History history) {
        Date date = new Date(System.currentTimeMillis());
        Car currentCar = rentService.getCurrentCar(history.getCarNumber());
        if(!currentCar.isAvailable()) {
            return ResponseEntity.badRequest().build();
        }
        history.setStartDate(date);
        history.setStartPoint(currentCar.getCurrentPoint());
        history.setCarName(currentCar.getName());
        historyService.createHistory(history);
        rentService.rentCar(history);
        rentService.setCarList(currentCar);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(history.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/cars/return/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Object> updateHistory(@RequestBody History history, @PathVariable long id) {
        Optional<History> historyNote = historyService.getHistory(id);

        if(!historyNote.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        history.setId(id);
        Date date = new Date(System.currentTimeMillis());
        history.setFinalDate(date);
        history.setStartDate(historyNote.get().getStartDate());
        history.setStartPoint(historyNote.get().getStartPoint());
        history.setCarName(historyNote.get().getCarName());

        rentService.returnCar(history, history.getFinalPoint());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/history/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteHistory(@PathVariable long id) {
        historyService.deleteHistory(id);
    }
}
