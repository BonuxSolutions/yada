package bonux.yada.repos;

import bonux.yada.repos.model.ModelTodoBuilder;
import bonux.yada.repos.model.Todo;
import bonux.yada.repos.model.TodoRowMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.H2SequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static bonux.yada.repos.TodoRepo.versionDoesNotMatch;

public interface TodoRepo {

    class VersionDoesNotMatch extends RuntimeException {
        private VersionDoesNotMatch(String msg) {
            super(msg);
        }
    }

    static VersionDoesNotMatch versionDoesNotMatch(Integer id, Integer version) {
        return new VersionDoesNotMatch(String.format("version {%d} does not match: id{%d}", version, id));
    }

    Optional<Todo> findById(Integer id);

    Collection<Todo> findAll();

    Todo create(Todo todo);

    Todo update(Todo todo);

    void deleteById(Integer id);
}

@Repository("todoRepository")
class TodoRepoImpl implements TodoRepo {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private AbstractSequenceMaxValueIncrementer incrementer;
    private RowMapper<Todo> rowMapper = new TodoRowMapper();

    @Override
    public Optional<Todo> findById(Integer id) {
        Map<String, Integer> namedParameters = Collections.singletonMap("id", id);

        return jdbcTemplate.query(
                "SELECT * FROM todo WHERE id = :id",
                namedParameters,
                rowMapper).stream().findFirst();
    }

    @Override
    public Collection<Todo> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM todo",
                rowMapper);
    }

    @Transactional
    @Override
    public Todo create(Todo todo) {
        Integer id = incrementer.nextIntValue();
        Todo todo1 = ModelTodoBuilder.from(todo)
                .withId(id)
                .build();
        jdbcTemplate.update("INSERT INTO todo " +
                        "(ID, TASK, TASK_STATE, CLOSE_REASON, TASK_START, TASK_END, " +
                        "CREATED, CREATED_BY, UPDATED, UPDATED_BY, VERSION) " +
                        "VALUES(:id, :task, :taskState, :closeReason, :taskStart, :taskEnd, " +
                        ":created, :createdBy, :updated, :updatedBy, :version)",
                todo1.asMap());

        return todo1;
    }

    @Transactional
    @Override
    public Todo update(Todo todo) {
        int r = jdbcTemplate.update("UPDATE todo SET " +
                        "task = :task, " +
                        "task_state = :taskState, " +
                        "close_reason = :closeReason, " +
                        "task_start = :taskStart, " +
                        "task_end = :taskEnd, " +
                        "updated = :updated, " +
                        "updated_by = :updatedBy, " +
                        "version = :version + 1" +
                        "WHERE id = :id " +
                        "AND version = :version",
                todo.asMap());
        if (r > 0) {
            return ModelTodoBuilder.from(todo).incrementVersion().build();
        } else {
            throw versionDoesNotMatch(todo.id, todo.version);
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE todo WHERE id = :id", Collections.singletonMap("id", id));
    }
}

@Configuration
class RepositoryConfiguration {

    @Bean
    @ConfigurationProperties("yada.datasource")
    HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Profile("inmem")
    AbstractSequenceMaxValueIncrementer h2Incrementer() {
        return dbIncrementer.apply(new H2SequenceMaxValueIncrementer());
    }

    @Bean
    @Profile("!inmem")
    AbstractSequenceMaxValueIncrementer postgresIncrementer() {
        return dbIncrementer.apply(new PostgresSequenceMaxValueIncrementer());
    }

    private Function<AbstractSequenceMaxValueIncrementer, AbstractSequenceMaxValueIncrementer> dbIncrementer =
            incrementer -> {
                incrementer.setDataSource(dataSource());
                incrementer.setIncrementerName("todo_id_seq");
                return incrementer;
            };
}