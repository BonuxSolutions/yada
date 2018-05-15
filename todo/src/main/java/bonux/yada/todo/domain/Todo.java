package bonux.yada.todo.domain;

import bonux.yada.todo.types.CloseReason;
import bonux.yada.todo.types.TaskState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

public final class Todo extends ResourceSupport {
    public Integer id;
    public String task;
    public TaskState taskState;
    public CloseReason closeReason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskEnd;
    @JsonIgnore
    private String user;
    public Integer version;

    Todo() {
    }

    Todo(Integer id,
         String task,
         TaskState taskState,
         CloseReason closeReason,
         LocalDateTime taskStart,
         LocalDateTime taskEnd,
         Integer version) {
        this.id = id;
        this.task = task;
        this.taskState = taskState;
        this.closeReason = closeReason;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.version = version;
    }

    public Todo withUser(String user) {
        this.user = user;
        return this;
    }

    public String user() {
        return this.user;
    }
}
