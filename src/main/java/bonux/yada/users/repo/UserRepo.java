package bonux.yada.users.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bonux.yada.users.model.User;

@Repository("userRepository")
public interface UserRepo extends JpaRepository<User, String> {

}
