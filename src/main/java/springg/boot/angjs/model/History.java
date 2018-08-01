package springg.boot.angjs.model;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String carName;
    @NotNull
    private String carNumber;
    @NotNull
    private String renterName;
    @NotNull
    private String startPoint;

    private String finalPoint;

    @CreatedDate()
    @Temporal(TIMESTAMP)
    private Date startDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date finalDate;

    public History() {
    }

    public History(String carName, String carNumber, String renterName, String startPoint, String finalPoint, Date startDate, Date finalDate) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.renterName = renterName;
        this.startPoint = startPoint;
        this.finalPoint = finalPoint;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(String finalPoint) {
        this.finalPoint = finalPoint;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
}
