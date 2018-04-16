package lv.yada.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lv.yada.types.CloseReason;
import lv.yada.types.TaskState;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lv.yada.types.TaskState.NEW;

@Entity
@TypeDef(
        name = "enum_type",
        typeClass = EnumTypeMapping.class
)
public final class Todo extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_seq")
    @SequenceGenerator(name = "todo_id_seq", sequenceName = "todo_id_seq")
    @Column
    private Integer id;

    @Column
    public String task;

    @Column
    @Enumerated(EnumType.STRING)
    @Type(type = "enum_type")
    public TaskState taskState;

    @Column
    @Enumerated(EnumType.STRING)
    @Type(type = "enum_type")
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

    public Todo newItemBy(String user) {
        this.createdBy = user;
        this.created = LocalDateTime.now();
        this.taskState = NEW;
        return updatedBy(user);
    }

    private Todo updatedBy(String user) {
        this.updatedBy = user;
        this.updated = LocalDateTime.now();
        return this;
    }

}
