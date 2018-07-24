package springg.boot.angjs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springg.boot.angjs.model.RentPoint;

import java.awt.*;

@RepositoryRestResource
public interface RentPointRepository extends JpaRepository<RentPoint, Long> {
    RentPoint getRentPointByAddress(String address);
}
