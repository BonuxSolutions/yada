package bonux.yada.services;

import bonux.yada.model.Todo;
import bonux.yada.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

public interface YadaServices {
    Optional<Todo> update(Integer id,
                          Todo.UpdateTodo updateTodo,
                          String userName);

    Todo create(Todo.CreateTodo createTodo,
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
    public Optional<Todo> update(Integer id,
                                 Todo.UpdateTodo updateTodo,
                                 String userName) {
        Optional<Todo> maybeTodo = todoRepo
                .findById(id)
                .map(todo -> Todo.copy(todo).update(updateTodo, userName).build());
        return maybeTodo.map(todo -> todoRepo.update(todo));
    }

    @Override
    public Todo create(Todo.CreateTodo createTodo, String userName) {
        Todo todo = Todo
                .builder()
                .create(createTodo, userName)
                .build();
        return todoRepo.create(todo);
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