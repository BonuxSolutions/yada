package bonux.yada.api;

import bonux.yada.model.Todo;

public interface TodoApi<T> {
    T getSome();

    T getOne(Integer id);

    T create(Todo.CreateTodo createTodo,
             String userName);

    T update(Todo.UpdateTodo updateTodo,
             Integer id,
             String userName);

    T delete(Integer id);
}
