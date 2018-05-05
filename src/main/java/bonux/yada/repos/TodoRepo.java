package bonux.yada.repos;

import bonux.yada.model.Todo;
import bonux.yada.model.TodoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.H2SequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface TodoRepo {
    Optional<Todo> findById(Integer id);

    Collection<Todo> findAll();

    Todo create(Todo todo);

    Todo update(Todo todo);

    void deleteById(Integer id);
}

@Repository("userRepository")
class TodoRepoImpl implements TodoRepo {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private AbstractSequenceMaxValueIncrementer incrementer;
    private RowMapper<Todo> rowMapper = new TodoRowMapper();
    private SimpleJdbcInsert insert;

    @Autowired
    TodoRepoImpl(NamedParameterJdbcTemplate jdbcTemplate,
                 AbstractSequenceMaxValueIncrementer incrementer) {
        this.jdbcTemplate = jdbcTemplate;
        this.incrementer = incrementer;

        insert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());
        insert.setTableName("todo");
    }

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
        Todo todo1 = Todo.copy(todo)
                .withId(id)
                .build();
        insert.execute(todo1.asMap());

        return todo1;
    }

    @Transactional
    @Override
    public Todo update(Todo todo) {
        return null;
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {

    }
}

@Configuration
class RepositoryConfiguration {

    @Autowired
    private DataSource dataSource;

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
                incrementer.setDataSource(dataSource);
                incrementer.setIncrementerName("todo_id_seq");
                return incrementer;
            };
}