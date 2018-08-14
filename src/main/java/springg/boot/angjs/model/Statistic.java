package springg.boot.angjs.model;

import java.util.Dictionary;
import java.util.Map;

public class Statistic {

    private String rentPointName;

    private Map<String, Double> carModels;

    public Statistic(String rentPointName) {
        this.rentPointName = rentPointName;
    }

    public Statistic(String rentPointName, Map<String, Double> carModels) {
        this.rentPointName = rentPointName;
        this.carModels = carModels;
    }

    public String getRentPointName() {
        return rentPointName;
    }

    public void setRentPointName(String rentPointName) {
        this.rentPointName = rentPointName;
    }

    public Map<String, Double> getCarModels() {
        return carModels;
    }

    public void setCarModels(Map<String, Double> carModels) {
        this.carModels = carModels;
    }
}
