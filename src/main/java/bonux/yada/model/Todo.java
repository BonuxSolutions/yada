package bonux.yada.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Todo extends ResourceSupport {
    public final Integer id;
    public final String task;
    public final TaskState taskState;
    public final CloseReason closeReason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public final LocalDateTime taskStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public final LocalDateTime taskEnd;
    public final LocalDateTime created;
    public final String createdBy;
    public final LocalDateTime updated;
    public final String updatedBy;
    public final Integer version;

    Todo(Integer id,
         String task,
         TaskState taskState,
         CloseReason closeReason,
         LocalDateTime taskStart,
         LocalDateTime taskEnd,
         LocalDateTime created,
         String createdBy,
         LocalDateTime updated,
         String updatedBy,
         Integer version) {
        this.id = id;
        this.task = task;
        this.taskState = taskState;
        this.closeReason = closeReason;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.version = version;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("task", task);
        map.put("taskState", taskState.toString());
        map.put("closeReason", Optional.ofNullable(closeReason).map(CloseReason::toString).orElse(null));
        map.put("taskStart", taskStart);
        map.put("taskEnd", taskEnd);
        map.put("created", created);
        map.put("createdBy", createdBy);
        map.put("updated", updated);
        map.put("updatedBy", updatedBy);
        map.put("version", version);

        return map;
    }

    public static class CreateTodo {
        public String task;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskStart;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskEnd;

        @Override
        public String toString() {
            return new ToStringCreator(this)
                    .append("task", task)
                    .append("taskStart", taskStart.toString())
                    .append("taskEnd", taskEnd.toString())
                    .toString();
        }
    }

    public static class UpdateTodo {
        public String task;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskStart;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskEnd;
        public TaskState taskState;
        public CloseReason closeReason;

        @Override
        public String toString() {
            return new ToStringCreator(this)
                    .append("task", task)
                    .append("taskStart", taskStart)
                    .append("taskEnd", taskEnd)
                    .append("taskState", taskState)
                    .append("closeReason", closeReason)
                    .toString();
        }
    }

    public static TodoBuilder builder() {
        return new TodoBuilder();
    }

    public static TodoBuilder copy(Todo todo) {
        return new TodoBuilder(todo);
    }

}
