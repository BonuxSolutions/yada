package lv.yada.api;

import lv.yada.model.Todo;
import lv.yada.repos.TodoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/yada")
class TodoController {

    private static Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoRepo todoRepo;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void handle(Exception e) {
        logger.error(e.getMessage(), e);
    }

    @GetMapping(
            path = "/todos",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    HttpEntity<?> getSome() {
        logger.info("getSome");
        List<?> todos = todoRepo
                .findAll()
                .stream()
                .peek(todo -> todo.add(linkTo(methodOn(TodoController.class).getSome()).withSelfRel()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping(
            path = "/todos",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    HttpEntity<?> create(@RequestBody Todo todo) {
        logger.info("create {}", todo);
        Todo todo1 = todo.createdBy("authentication.getName()");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(todoRepo.save(todo1))
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
