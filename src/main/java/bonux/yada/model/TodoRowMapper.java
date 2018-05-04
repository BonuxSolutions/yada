package bonux.yada.model;

import bonux.yada.types.CloseReason;
import bonux.yada.types.TaskState;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoBuilder builder = Todo.builder();

        String closeReason = rs.getString("close_reason");

        builder.withId(rs.getInt("id"))
                .withTask(rs.getString("task"))
                .withTaskState(TaskState.valueOf(rs.getString("task_state")))
                .withCloseReason(closeReason != null ? CloseReason.valueOf(closeReason) : null)
                .withTaskStart(LocalDateTime.ofInstant(rs.getTimestamp("task_start").toInstant(), ZoneOffset.UTC))
                .withTaskEnd(LocalDateTime.ofInstant(rs.getTimestamp("task_end").toInstant(), ZoneOffset.UTC))
                .withCreated(LocalDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneOffset.UTC))
                .withCreatedBy(rs.getString("created_by"))
                .withUpdated(LocalDateTime.ofInstant(rs.getTimestamp("updated").toInstant(), ZoneOffset.UTC))
                .withUpdatedBy(rs.getString("updated_by"))
                .withVersion(rs.getInt("version"));

        return builder.build();
    }
}
