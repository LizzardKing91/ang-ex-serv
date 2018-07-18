package springg.boot.angjs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@ToString @EqualsAndHashCode
public class Car {
    @Id @GeneratedValue
    private Long id;
    private @NonNull String name;
    private @NonNull String number;
    private boolean isAvailable;
    private String currentPoint;
    @ManyToOne
    private RentPoint rentPoint;

    public
    Car() {
    }

    public
    Car(String name) {
        this.name = name;
    }

    public Car(String name, String number, boolean isAvailable, String currentPoint) {
        this.name = name;
        this.number = number;
        this.isAvailable = isAvailable;
        this.currentPoint = currentPoint;
    }

    public Car(String name, String number, boolean isAvailable, String currentPoint, RentPoint rentPoint) {
        this.name = name;
        this.number = number;
        this.isAvailable = isAvailable;
        this.currentPoint = currentPoint;
        this.rentPoint = rentPoint;
    }

    public
    Long getId() {
        return id;
    }

    public
    void setId(Long id) {
        this.id = id;
    }

    public
    String getName() {
        return name;
    }

    public
    void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(String currentPoint) {
        this.currentPoint = currentPoint;
    }

    @JsonIgnore
    public RentPoint getRentPoint() {
        return rentPoint;
    }

    public void setRentPoint(RentPoint rentPoint) {
        this.rentPoint = rentPoint;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", isAvailable=" + isAvailable +
                ", currentPoint='" + currentPoint + '\'' +
                '}';
    }
}