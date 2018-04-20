package bonux.yada.model;

import bonux.yada.types.CloseReason;
import com.fasterxml.jackson.annotation.JsonFormat;
import bonux.yada.types.TaskState;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.LocalDateTime;

import static bonux.yada.types.TaskState.NEW;

@Entity
@SequenceGenerator(name = "todo_id_seq", sequenceName = "todo_id_seq", allocationSize = 1)
public final class Todo extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_seq")
    @Column
    public Integer id;

    @Column
    public String task;

    @Column
    @Enumerated(EnumType.STRING)
    public TaskState taskState;

    @Column
    @Enumerated(EnumType.STRING)
    public CloseReason closeReason;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskStart;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime taskEnd;

    @Column
    private LocalDateTime created;

    @Column
    private String createdBy;

    @Column
    private LocalDateTime updated;

    @Column
    private String updatedBy;

    public Todo forUpdate(UpdateTodo updateTodo, String userName) {
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

        return this;
    }

    public static class CreateTodo {
        public String task;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskStart;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        public LocalDateTime taskEnd;

        public Todo withCreator(String userName) {
            Todo todo = new Todo();

            todo.task = task;
            todo.taskState = NEW;
            todo.taskStart = taskStart;
            todo.taskEnd = taskEnd;

            LocalDateTime localDateTime = LocalDateTime.now();

            todo.created = localDateTime;
            todo.createdBy = userName;
            todo.updated = localDateTime;
            todo.updatedBy = userName;

            return todo;
        }

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
}
