package springg.boot.angjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.service.CarService;
import springg.boot.angjs.service.CarServiceImpl;

import java.util.Collection;
import java.util.List;

@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarServiceImpl carService){
        this.carService = carService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView cars() {
        ModelAndView mav = new ModelAndView("cars/list");
        mav.addObject("cars", carService.getAllCars());
        return mav;
    }

    @ModelAttribute("cars")
    public List<Car> messages() {
        return carService.getAllCars();
    }
}
