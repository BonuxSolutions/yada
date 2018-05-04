package bonux.yada.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class Todo extends ResourceSupport {
    public Integer id;
    public String task;
    public TaskState taskState;
    public CloseReason closeReason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskEnd;
    public LocalDateTime created;
    public String createdBy;
    public LocalDateTime updated;
    public String updatedBy;
    public Integer version;

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("task", task);
        map.put("task_state", taskState);
        map.put("close_reason", closeReason);
        map.put("task_start", taskStart);
        map.put("task_end", taskEnd);
        map.put("created", created);
        map.put("created_by", createdBy);
        map.put("updated", updated);
        map.put("updated_by", updatedBy);
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
