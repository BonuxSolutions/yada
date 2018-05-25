package bonux.yada.todo.repos.model;

import bonux.yada.todo.types.CloseReason;
import bonux.yada.todo.types.TaskState;
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
        var builder = ModelTodoBuilder.builder();

        var taskState = Optional
                .ofNullable(rs.getString("task_state"))
                .map(TaskState::valueOf)
                .orElseThrow();
        var closeReason = Optional
                .ofNullable(rs.getString("close_reason"))
                .map(CloseReason::valueOf)
                .orElse(null);
        var taskStart = Optional
                .ofNullable(rs.getTimestamp("task_start"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        var taskEnd = Optional
                .ofNullable(rs.getTimestamp("task_end"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        var created = Optional
                .ofNullable(rs.getTimestamp("created"))
                .map(Timestamp::toInstant)
                .map(i -> LocalDateTime.ofInstant(i, ZoneOffset.UTC))
                .orElseThrow();
        var updated = Optional
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
