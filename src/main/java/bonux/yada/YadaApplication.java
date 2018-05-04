package bonux.yada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackageClasses = {YadaApplication.class})
@SpringBootApplication
@EnableTransactionManagement
@EnableWebSecurity
public class YadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YadaApplication.class, args);
    }
}
