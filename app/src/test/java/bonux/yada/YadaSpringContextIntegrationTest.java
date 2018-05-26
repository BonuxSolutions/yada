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

    @Test
    public void testSpringContext() {
        assertNotNull(applicationContext);
    }

    @Test
    public void testUserDetailsService() {
        var user = userDetailsService.loadUserByUsername("admin");
        var authorities = new ArrayList<>(user.getAuthorities());
        assertEquals("admin", user.getUsername());
        assertTrue(authorities.stream().anyMatch(p -> p.getAuthority().equals(Roles.ADMIN.toString())));
    }
}
