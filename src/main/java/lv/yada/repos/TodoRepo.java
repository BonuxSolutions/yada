package lv.yada.repos;

import lv.yada.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface TodoRepo extends JpaRepository<Todo, Integer> {

}
