package lv.yada.api;

import lv.yada.types.CloseReason;
import lv.yada.types.TaskState;

import java.time.LocalDateTime;

public final class Todo {
    public final String task;

    public final TaskState taskState;

    public final CloseReason closeReason;

    public final LocalDateTime taskStart;

    public final LocalDateTime taskEnd;

    private Todo(String task,
                 TaskState taskState,
                 CloseReason closeReason,
                 LocalDateTime taskStart,
                 LocalDateTime taskEnd) {
        this.task = task;
        this.taskState = taskState;
        this.closeReason = closeReason;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
    }

    public lv.yada.model.Todo createBy(String user) {
        lv.yada.model.Todo todo = new lv.yada.model.Todo();
        todo.task = task;
        todo.taskState = taskState;
        todo.closeReason = closeReason;
        todo.taskStart = taskStart;
        todo.taskEnd = taskEnd;
        todo.created = LocalDateTime.now();
        todo.createdBy = user;
        todo.updated = LocalDateTime.now();
        todo.updatedBy = user;

        return todo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String task;
        private TaskState taskState;
        private CloseReason closeReason;
        private LocalDateTime taskStart;
        private LocalDateTime taskEnd;

        private Builder() {
        }

        public Builder fromModel(lv.yada.model.Todo todo) {
            this.task = todo.task;
            this.taskState = todo.taskState;
            this.closeReason = todo.closeReason;
            this.taskStart = todo.taskStart;
            this.taskEnd = todo.taskEnd;
            return this;
        }

        public Builder setTask(String task) {
            this.task = task;
            return this;
        }

        public Builder setTaskState(TaskState taskState) {
            this.taskState = taskState;
            return this;
        }

        public Builder setCloseReason(CloseReason closeReason) {
            this.closeReason = closeReason;
            return this;
        }

        public Builder setTaskStart(LocalDateTime taskStart) {
            this.taskStart = taskStart;
            return this;
        }

        public Builder setTaskEnd(LocalDateTime taskEnd) {
            this.taskEnd = taskEnd;
            return this;
        }

        public Todo build() {
            return new Todo(task, taskState, closeReason, taskStart, taskEnd);
        }
    }
}
