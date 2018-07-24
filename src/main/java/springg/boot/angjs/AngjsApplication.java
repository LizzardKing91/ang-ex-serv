package springg.boot.angjs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springg.boot.angjs.repository.CarRepository;

@SpringBootApplication
@EnableJpaAuditing
public
class AngjsApplication {

    public static
    void main(String[] args) {
        SpringApplication.run(AngjsApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CarRepository repository) {
        return args -> {};
    }
}
