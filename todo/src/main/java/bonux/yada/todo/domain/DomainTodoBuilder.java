package bonux.yada.todo.domain;

public final class DomainTodoBuilder {

    public static Todo fromModel(bonux.yada.todo.repos.model.Todo todo) {
        return new Todo(
                todo.id,
                todo.task,
                todo.taskState,
                todo.closeReason,
                todo.taskStart,
                todo.taskEnd,
                todo.version);
    }
}
