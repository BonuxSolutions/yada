package bonux.yada;

import bonux.yada.auth.Roles;
import bonux.yada.auth.YadaUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YadaUserDetailsService.class})
@DataJpaTest
public class YadaSpringContextIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void testSpringContext() {
        assertNotNull(applicationContext);
    }

    @Test
    public void testUserDetailsService() {
        var admin = userDetailsService.loadUserByUsername("admin");
        var user = userDetailsService.loadUserByUsername("user");
        var authorities = new ArrayList<>(admin.getAuthorities());

        assertEquals("admin", admin.getUsername());
        assertEquals("user", user.getUsername());

        assertTrue(authorities.stream().anyMatch(p -> p.getAuthority().equals(Roles.ADMIN.toString())));

        assertTrue(passwordEncoder.matches("admin", admin.getPassword()));
        assertTrue(passwordEncoder.matches("user", user.getPassword()));
    }
}
