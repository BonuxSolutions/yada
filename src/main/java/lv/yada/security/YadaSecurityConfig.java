package lv.yada.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import static lv.yada.security.Roles.ADMIN;
import static lv.yada.security.Roles.USER;

@Service
@EnableWebSecurity
class YadaSecurityConfig
        extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("admin")).roles(ADMIN.name(), USER.name())
                .and()
                .withUser("user").password(passwordEncoder.encode("user")).roles(USER.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/yada/todos").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers(HttpMethod.POST, "/yada/todos").hasAuthority(ADMIN.name()).anyRequest()
                .authenticated().and().headers().and().csrf().disable();
    }

    @Bean
    UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }
}
