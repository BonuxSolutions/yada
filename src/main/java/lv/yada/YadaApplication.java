package lv.yada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EntityScan(basePackageClasses = {YadaApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
public class YadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YadaApplication.class, args);
    }
}
