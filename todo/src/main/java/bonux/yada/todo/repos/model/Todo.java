package bonux.yada.todo.repos.model;

import bonux.yada.todo.types.CloseReason;
import bonux.yada.todo.types.TaskState;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Todo {
    public final Integer id;
    public final String task;
    public final TaskState taskState;
    public final CloseReason closeReason;
    public final LocalDateTime taskStart;
    public final LocalDateTime taskEnd;
    final LocalDateTime created;
    final String createdBy;
    final LocalDateTime updated;
    final String updatedBy;
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
        var map = new HashMap<String, Object>();

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
}
