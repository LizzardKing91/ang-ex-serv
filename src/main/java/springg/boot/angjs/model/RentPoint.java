package springg.boot.angjs.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@EqualsAndHashCode
public class RentPoint {

    @Id
    @GeneratedValue
    private Long id;

    private String address;

    @OneToMany(mappedBy = "rentPoint")
    private List<Car> carList;

    public RentPoint() {
    }

    public RentPoint(String address, List<Car> carList) {
        this.address = address;
        this.carList = carList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public String toString() {
        return "RentPoint{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", carList=" + carList +
                '}';
    }
}
