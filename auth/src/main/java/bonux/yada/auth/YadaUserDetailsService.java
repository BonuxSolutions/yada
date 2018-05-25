package bonux.yada.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
public class YadaUserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService = new JdbcDaoImpl();
        userDetailsService.setJdbcTemplate(jdbcTemplate);

        return userDetailsService;
    }
}
