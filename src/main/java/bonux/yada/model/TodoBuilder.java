package bonux.yada.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;

import java.time.LocalDateTime;
import java.util.Objects;

import static bonux.yada.types.TaskState.NEW;

public final class TodoBuilder {
    private Integer id;
    private String task;
    private TaskState taskState;
    private CloseReason closeReason;
    private LocalDateTime taskStart;
    private LocalDateTime taskEnd;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime updated;
    private String updatedBy;
    private Integer version;

    TodoBuilder() {
    }

    TodoBuilder(Todo todo) {
        this.id = todo.id;
        this.task = todo.task;
        this.taskState = todo.taskState;
        this.closeReason = todo.closeReason;
        this.taskStart = todo.taskStart;
        this.taskEnd = todo.taskEnd;
        this.created = todo.created;
        this.createdBy = todo.createdBy;
        this.updated = todo.updated;
        this.updatedBy = todo.updatedBy;
        this.version = todo.version;
    }

    public TodoBuilder withId(Integer id) {
        this.id = Objects.requireNonNull(id);
        return this;
    }

    public TodoBuilder withTask(String task) {
        this.task = Objects.requireNonNull(task);
        return this;
    }

    public TodoBuilder withTaskState(TaskState taskState) {
        this.taskState = taskState;
        return this;
    }

    public TodoBuilder withCloseReason(CloseReason closeReason) {
        this.closeReason = closeReason;
        return this;
    }

    public TodoBuilder withTaskStart(LocalDateTime taskStart) {
        this.taskStart = taskStart;
        return this;
    }

    public TodoBuilder withTaskEnd(LocalDateTime taskEnd) {
        this.taskEnd = taskEnd;
        return this;
    }

    public TodoBuilder withCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public TodoBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public TodoBuilder withUpdated(LocalDateTime updated) {
        this.updated = updated;
        return this;
    }

    public TodoBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public TodoBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    public TodoBuilder create(Todo.CreateTodo createTodo, String userName) {
        this.task = createTodo.task;
        this.taskState = NEW;
        this.taskStart = createTodo.taskStart;
        this.taskEnd = createTodo.taskEnd;

        LocalDateTime localDateTime = LocalDateTime.now();

        this.created = localDateTime;
        this.createdBy = userName;
        this.updated = localDateTime;
        this.updatedBy = userName;
        this.version = 0;

        return this;
    }

    public TodoBuilder update(Todo.UpdateTodo updateTodo, String userName) {
        if (updateTodo.task != null) {
            this.task = updateTodo.task;
        }
        if (updateTodo.closeReason != null) {
            this.closeReason = updateTodo.closeReason;
        }
        if (updateTodo.taskState != null) {
            this.taskState = updateTodo.taskState;
        }
        if (updateTodo.taskStart != null) {
            this.taskStart = updateTodo.taskStart;
        }
        if (updateTodo.taskEnd != null) {
            this.taskEnd = updateTodo.taskEnd;
        }

        this.updated = LocalDateTime.now();
        this.updatedBy = userName;
        this.version = this.version + 1;

        return this;
    }

    public Todo build() {
        return new Todo(
                id,
                task,
                taskState,
                closeReason,
                taskStart,
                taskEnd,
                created,
                createdBy,
                updated,
                updatedBy,
                version);
    }
}
