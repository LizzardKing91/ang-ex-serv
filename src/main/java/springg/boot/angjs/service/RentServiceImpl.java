package springg.boot.angjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springg.boot.angjs.model.Car;
import springg.boot.angjs.model.History;
import springg.boot.angjs.model.RentPoint;
import springg.boot.angjs.model.Statistic;
import springg.boot.angjs.repository.CarRepository;
import springg.boot.angjs.repository.HistoryRepository;
import springg.boot.angjs.repository.RentPointRepository;

import java.util.*;
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
    public void returnCar(History history) {
        Car rentedCar = carRepository.getCarByNumber(history.getCarNumber());

        rentedCar.setAvailable(true);
        rentedCar.setCurrentPoint(history.getFinalPoint());

        List<History> historyList = rentedCar.getHistoryList();
        historyList.add(history);

        rentedCar.setHistoryList(historyList);

        RentPoint point = rentPointRepository.getRentPointByAddress(rentedCar.getCurrentPoint());
        point.getCarList().add(rentedCar);

        rentPointRepository.save(point);
        historyRepository.save(history);
        carRepository.save(rentedCar);
    }

    public double getAverageTime(String address, String carName){
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
            sum += getDateDiff(history.getStartDate(), history.getFinalDate());
        }

        // get average and convert to milliseconds
        return (sum / historyList.size()) / 1000;
    }

    @Override
    public List<Statistic> getAverageTimeList() {
        List<Car> cars = carRepository.findAll();
        List<String> carModels = cars.stream().map(Car::getName).distinct().collect(Collectors.toList());

        List<RentPoint> points = rentPointRepository.findAll();
        List<String> pointsAddresses = points.stream().map(RentPoint::getAddress).collect(Collectors.toList());

        List<Statistic> statisticList = new ArrayList<>();

        for (String pointAddress: pointsAddresses
             ) {
            Statistic statistic = new Statistic(pointAddress);
            Map<String, Double> pointCars = new HashMap<String, Double>() {
            };
            statistic.setCarModels(pointCars);
            for (String carModel: carModels
                 ) {
                double averageTime = getAverageTime(pointAddress, carModel);

                statistic.getCarModels().put(carModel, averageTime);
            }
            statisticList.add(statistic);
        }

        return statisticList;
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
    public void updateHistoryList(Car car) {
        if(!car.getHistoryList().isEmpty()) {
            for (History history: car.getHistoryList()
                 ) {
                history.setCarName(car.getName());
                history.setCarNumber(car.getNumber());
                historyRepository.save(history);
            }
        }
    }

    @Override
    public void updateRentPointAddressForCar(RentPoint point, String oldAddress) {
        if(!point.getCarList().isEmpty()) {
            String newAddress = point.getAddress();
            for (Car car: point.getCarList()
            ) {
                car.setCurrentPoint(point.getAddress());
                if (!car.getHistoryList().isEmpty()) {
                    for (History history: car.getHistoryList()
                    ) {
                        if(history.getStartPoint().equals(oldAddress)) {
                            history.setStartPoint(newAddress);
                        }
                        if(history.getFinalPoint().equals(oldAddress)) {
                            history.setFinalPoint(newAddress);
                        }
                        historyRepository.save(history);
                    }
                }
                carRepository.save(car);
            }
        }
    }

    @Override
    public void deleteRentPointAddressForCar(RentPoint point) {
        if(point.getCarList().isEmpty()) {
            return;
        }

        for (Car car: point.getCarList()
             ) {
            car.setCurrentPoint(null);

            carRepository.save(car);
        }
    }

    @Override
    public void deleteCarFromCarList(String address, Long id) {
        if(address != null){
            RentPoint point = rentPointRepository.getRentPointByAddress(address);
            List<Car> newCarList = point.getCarList();
            newCarList.removeIf(car -> car.getId().equals(id));
            point.setCarList(newCarList);
            rentPointRepository.save(point);
        }
    }

    @Override
    public boolean checkRentPoint(String address) {
        boolean match = rentPointRepository.findAll().stream().anyMatch(rentPoint -> rentPoint.getAddress().equals(address));
        return match;
    }

    private static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
