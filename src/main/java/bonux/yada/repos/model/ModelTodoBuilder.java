package bonux.yada.repos.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;

import java.time.LocalDateTime;
import java.util.Objects;

import static bonux.yada.types.TaskState.NEW;

public final class ModelTodoBuilder {
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

    private ModelTodoBuilder() {
    }

    private ModelTodoBuilder(Todo todo) {
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

    public static ModelTodoBuilder builder() {
        return new ModelTodoBuilder();
    }

    public static ModelTodoBuilder from(Todo todo) {
        return new ModelTodoBuilder(todo);
    }

    public ModelTodoBuilder update(bonux.yada.domain.Todo todo){
            if (todo.task != null) {
                this.task = todo.task;
            }
            if (todo.closeReason != null) {
                this.closeReason = todo.closeReason;
            }
            if (todo.taskState != null) {
                this.taskState = todo.taskState;
            }
            if (todo.taskStart != null) {
                this.taskStart = todo.taskStart;
            }
            if (todo.taskEnd != null) {
                this.taskEnd = todo.taskEnd;
            }

            this.updated = LocalDateTime.now();
            this.updatedBy = todo.user();

            return this;
    }

    public ModelTodoBuilder create(bonux.yada.domain.Todo todo) {
        this.task = todo.task;
        this.taskState = NEW;
        this.taskStart = todo.taskStart;
        this.taskEnd = todo.taskEnd;

        LocalDateTime localDateTime = LocalDateTime.now();

        this.created = localDateTime;
        this.createdBy = todo.user();
        this.updated = localDateTime;
        this.updatedBy = todo.user();
        this.version = 0;

        return this;
    }

    public ModelTodoBuilder withId(Integer id) {
        this.id = Objects.requireNonNull(id);
        return this;
    }

    public ModelTodoBuilder withTask(String task) {
        this.task = Objects.requireNonNull(task);
        return this;
    }

    public ModelTodoBuilder withTaskState(TaskState taskState) {
        this.taskState = taskState;
        return this;
    }

    public ModelTodoBuilder withCloseReason(CloseReason closeReason) {
        this.closeReason = closeReason;
        return this;
    }

    public ModelTodoBuilder withTaskStart(LocalDateTime taskStart) {
        this.taskStart = taskStart;
        return this;
    }

    public ModelTodoBuilder withTaskEnd(LocalDateTime taskEnd) {
        this.taskEnd = taskEnd;
        return this;
    }

    public ModelTodoBuilder withCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public ModelTodoBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ModelTodoBuilder withUpdated(LocalDateTime updated) {
        this.updated = updated;
        return this;
    }

    public ModelTodoBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ModelTodoBuilder withVersion(Integer version) {
        this.version = Objects.requireNonNull(version);
        return this;
    }

    public ModelTodoBuilder incrementVersion() {
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
