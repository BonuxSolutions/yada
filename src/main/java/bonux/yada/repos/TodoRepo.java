package bonux.yada.repos;

import bonux.yada.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface TodoRepo extends JpaRepository<Todo, Integer> {

}
