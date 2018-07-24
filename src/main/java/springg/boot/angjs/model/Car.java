package springg.boot.angjs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@EqualsAndHashCode
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private @NonNull String name;
    private @NonNull @Column(unique = true) String number;
    private boolean isAvailable;
    @Nullable
    private String currentPoint;

    @OneToMany
    private List<History> historyList;

    @ManyToOne
    @JsonIgnore
    private RentPoint point;

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

    public Car(String name, String number, boolean isAvailable, String currentPoint, List<History> historyList) {
        this.name = name;
        this.number = number;
        this.isAvailable = isAvailable;
        this.currentPoint = currentPoint;
        this.historyList = historyList;
    }

    public Car(String name, String number, boolean isAvailable, String currentPoint, List<History> historyList, RentPoint point) {
        this.name = name;
        this.number = number;
        this.isAvailable = isAvailable;
        this.currentPoint = currentPoint;
        this.historyList = historyList;
        this.point = point;
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

    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    public RentPoint getPoint() {
        return point;
    }

    public void setPoint(RentPoint point) {
        this.point = point;
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