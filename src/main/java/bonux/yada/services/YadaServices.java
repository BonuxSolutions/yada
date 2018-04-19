package bonux.yada.services;

import bonux.yada.model.Todo;
import bonux.yada.repos.TodoRepo;
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

    Optional<Todo> get(Integer id);

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
    public Optional<Todo> get(Integer id) {
        return todoRepo.findById(id);
    }

    @Override
    public Stream<Todo> get() {
        return todoRepo.findAll().stream();
    }
}