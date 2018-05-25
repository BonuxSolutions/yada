package bonux.yada.todo.services.impl;

import bonux.yada.todo.domain.DomainTodoBuilder;
import bonux.yada.todo.domain.Todo;
import bonux.yada.todo.repos.TodoRepo;
import bonux.yada.todo.repos.model.ModelTodoBuilder;
import bonux.yada.todo.services.YadaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class YadaServicesImpl
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
