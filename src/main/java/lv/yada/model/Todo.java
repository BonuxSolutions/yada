package lv.yada.model;

import lv.yada.types.CloseReason;
import lv.yada.types.TaskState;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public final class Todo extends ResourceSupport {
    @Id
    @GeneratedValue
    @Column
    public int id;

    @Column
    public String task;

    @Column
    @Enumerated(EnumType.STRING)
    public TaskState taskState;

    @Column
    @Enumerated(EnumType.STRING)
    public CloseReason closeReason;

    @Column
    public LocalDateTime taskStart;

    @Column
    public LocalDateTime taskEnd;

    @Column
    public LocalDateTime created;

    @Column
    public String createdBy;

    @Column
    public LocalDateTime updated;

    @Column
    public String updatedBy;

    public Todo createdBy(String user) {
        this.createdBy = user;
        this.created = LocalDateTime.now();
        return updatedBy(user);
    }

    public Todo updatedBy(String user) {
        this.updatedBy = user;
        this.updated = LocalDateTime.now();
        return this;
    }

}
