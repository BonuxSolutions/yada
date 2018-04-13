package lv.yada.api;

import lv.yada.repos.TodoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/yada")
public final class TodoController {

    private static Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    private TodoRepo todoRepo;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle(Exception e) {
        logger.error(e.getMessage(), e);
    }

    @RequestMapping(
            path = "/todos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<Todo> getSome() {
        logger.info("getSome");
        return todoRepo
                .findAll(PageRequest.of(1, 20))
                .map(todo -> Todo.builder().fromModel(todo).build())
                .getContent();
    }

    @RequestMapping(
            path = "/todos",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> create(@RequestBody Todo todo,
                                    Authentication authentication) {
        logger.info("create {}", todo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(todoRepo.save(todo.createBy(authentication.getName())))
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
