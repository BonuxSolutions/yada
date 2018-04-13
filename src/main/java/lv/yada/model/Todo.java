package lv.yada.model;

import lv.yada.types.CloseReason;
import lv.yada.types.TaskState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public final class Todo {
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
}
