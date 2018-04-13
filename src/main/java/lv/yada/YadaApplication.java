package lv.yada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {YadaApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
@EnableJpaRepositories
public class YadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YadaApplication.class, args);
    }
}
