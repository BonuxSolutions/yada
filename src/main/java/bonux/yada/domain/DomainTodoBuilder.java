package bonux.yada.domain;

public final class DomainTodoBuilder {

    public static Todo fromModel(bonux.yada.repos.model.Todo todo) {
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
