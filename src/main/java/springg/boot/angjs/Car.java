package springg.boot.angjs;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

@Entity
@ToString @EqualsAndHashCode
public class Car {
    @Id @GeneratedValue
    private Long id;
    private @NonNull String name;

    public
    Car() {
    }

    public
    Car(String name) {
        this.name = name;
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
}