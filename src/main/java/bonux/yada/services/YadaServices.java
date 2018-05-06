package bonux.yada.services;

import bonux.yada.domain.DomainTodoBuilder;
import bonux.yada.domain.Todo;
import bonux.yada.repos.TodoRepo;
import bonux.yada.repos.model.ModelTodoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

public interface YadaServices {

    Optional<Todo> update(Integer id,
                          Todo updateTodo);

    Todo create(Todo createTodo);

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
                                 Todo updateTodo) {
        var maybeTodo = todoRepo
                .findById(id)
                .map(todo -> ModelTodoBuilder.from(todo).update(updateTodo).build());
        return maybeTodo.map(todo -> DomainTodoBuilder.fromModel(todoRepo.update(todo)));
    }

    @Override
    public Todo create(Todo todo) {
        return DomainTodoBuilder.fromModel(todoRepo.create(ModelTodoBuilder.builder().create(todo).build()));
    }

    @Override
    public Optional<Todo> get(Integer id) {
        return todoRepo.findById(id).map(DomainTodoBuilder::fromModel);
    }

    @Override
    public Stream<Todo> get() {
        return todoRepo.findAll().stream().map(DomainTodoBuilder::fromModel);
    }
}