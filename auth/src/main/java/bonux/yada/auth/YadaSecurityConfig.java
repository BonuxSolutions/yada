package bonux.yada.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static bonux.yada.auth.Roles.ADMIN;
import static bonux.yada.auth.Roles.USER;

@Service
public class YadaSecurityConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .requestCache()
                    .disable()
                .httpBasic()
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/yada/todos", "/yada/todos/", "/yada/todos/**").hasAnyAuthority(USER.name(), ADMIN.name())
                    .antMatchers(HttpMethod.PUT, "/yada/todos/**").hasAnyAuthority(USER.name(), ADMIN.name())
                    .antMatchers(HttpMethod.POST, "/yada/todos", "/yada/todos/").hasAuthority(ADMIN.name())
                    .antMatchers(HttpMethod.DELETE, "/yada/todos/**").hasAuthority(ADMIN.name());
    }
}

