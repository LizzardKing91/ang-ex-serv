package springg.boot.angjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.repository.CarRepository;
import springg.boot.angjs.repository.HistoryRepository;
import springg.boot.angjs.repository.RentPointRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RentServiceImpl implements RentService {
    private HistoryRepository historyRepository;
    private RentPointRepository rentPointRepository;
    private CarRepository carRepository;

    @Autowired
    public RentServiceImpl(HistoryRepository historyRepository, CarRepository carRepository, RentPointRepository rentPointRepository) {
        this.historyRepository = historyRepository;
        this.carRepository = carRepository;
        this.rentPointRepository = rentPointRepository;
    }

    @Override
    public void rentCar(History history) {
        Car rentedCar = carRepository.getCarByNumber(history.getCarNumber());
        RentPoint point = rentPointRepository.getRentPointByAddress(rentedCar.getCurrentPoint());
        point.getCarList().remove(rentedCar);
        rentedCar.setAvailable(false);
        rentedCar.setCurrentPoint(null);
        carRepository.save(rentedCar);
        rentPointRepository.save(point);
    }

    @Override
    public void returnCar(History history, String rentPointAddress) {
        Car rentedCar = carRepository.getCarByNumber(history.getCarNumber());

        rentedCar.setAvailable(true);
        rentedCar.setCurrentPoint(rentPointAddress);
        List<History> historyList = rentedCar.getHistoryList();
        historyList.add(history);
        rentedCar.setHistoryList(historyList);
        History updHistory = historyRepository.getOne(history.getId());
        updHistory.setFinalPoint(rentPointAddress);
        RentPoint point = rentPointRepository.getRentPointByAddress(rentedCar.getCurrentPoint());
        point.getCarList().add(rentedCar);

        rentPointRepository.save(point);
        historyRepository.save(updHistory);
        carRepository.save(rentedCar);
    }

    public float getAverageTime(String address, String carName){
        List<History> historyList = historyRepository.findAll().stream()
                .filter(history -> history.getStartPoint()
                        .equals(address) && history.getCarName()
                        .equals(carName)).collect(Collectors.toList());

        if(historyList.size() == 0)
        {
            return 0;
        }
        float sum = 0;

        for (History history: historyList
             ) {
            sum += getDateDiff(history.getStartDate(), history.getFinalDate(), TimeUnit.MILLISECONDS);
        }

        return (sum / historyList.size()) / 60000;
    }

    public Car getCurrentCar(String number) {
        return carRepository.getCarByNumber(number);
    }

    @Override
    public void setCarList(Car car) {
        if(car.getCurrentPoint() != null){
            RentPoint point = rentPointRepository.getRentPointByAddress(car.getCurrentPoint());
            List<Car> newCarList = point.getCarList();
            newCarList.add(car);
            point.setCarList(newCarList);
            rentPointRepository.save(point);
        }
    }

    @Override
    public void deleteCarFromCarList(Car car) {
        if(car.getCurrentPoint() != null){
            RentPoint point = rentPointRepository.getRentPointByAddress(car.getCurrentPoint());
            List<Car> newCarList = point.getCarList();
            newCarList.remove(car);
            point.setCarList(newCarList);
            rentPointRepository.save(point);
        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
