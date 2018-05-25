package bonux.yada.todo.services;

import bonux.yada.todo.domain.DomainTodoBuilder;
import bonux.yada.todo.domain.Todo;
import bonux.yada.todo.repos.TodoRepo;
import bonux.yada.todo.repos.model.ModelTodoBuilder;
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

