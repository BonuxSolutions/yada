package bonux.yada.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public final class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoBuilder builder = Todo.builder();

        TaskState taskState = Optional
                .ofNullable(rs.getString("task_state"))
                .map(TaskState::valueOf)
                .orElseThrow();
        CloseReason closeReason = Optional
                .ofNullable(rs.getString("close_reason"))
                .map(CloseReason::valueOf)
                .orElse(null);
        LocalDateTime taskStart = Optional
                .ofNullable(rs.getTimestamp("task_start"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        LocalDateTime taskEnd = Optional
                .ofNullable(rs.getTimestamp("task_end"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        LocalDateTime created = Optional
                .ofNullable(rs.getTimestamp("created"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        LocalDateTime updated = Optional
                .ofNullable(rs.getTimestamp("updated"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();

        return builder.withId(rs.getInt("id"))
                .withTask(rs.getString("task"))
                .withTaskState(taskState)
                .withCloseReason(closeReason)
                .withTaskStart(taskStart)
                .withTaskEnd(taskEnd)
                .withCreated(created)
                .withCreatedBy(rs.getString("created_by"))
                .withUpdated(updated)
                .withUpdatedBy(rs.getString("updated_by"))
                .withVersion(rs.getInt("version"))
                .build();
    }
}
