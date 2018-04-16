package lv.yada.services;

import lv.yada.model.Todo;
import lv.yada.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

public interface YadaServices {
    Optional<URI> update(Integer id,
                         Todo.UpdateTodo updateTodo,
                         String userName);

    URI create(Todo.CreateTodo createTodo,
               String userName);

    Stream<Todo> get();

    void delete(Integer id);
}

@Service
class YadaServicesImpl
        implements YadaServices {
    @Autowired
    private TodoRepo todoRepo;

    @Override
    public void delete(Integer id) {
        todoRepo.deleteById(id);
    }

    @Override
    public Optional<URI> update(Integer id,
                                Todo.UpdateTodo updateTodo,
                                String userName) {
        Optional<Todo> maybeTodo = todoRepo.findById(id).map(todo -> todo.forUpdate(updateTodo, userName));
        return maybeTodo.map(todo -> ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(todoRepo.save(todo))
                .toUri());
    }

    @Override
    public URI create(Todo.CreateTodo createTodo, String userName) {
        Todo todo1 = createTodo.withCreator(userName);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(todoRepo.save(todo1))
                .toUri();
    }

    @Override
    public Stream<Todo> get() {
        return todoRepo.findAll().stream();
    }
}