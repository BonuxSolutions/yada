package lv.yada.api;

import lv.yada.model.Todo;
import lv.yada.services.YadaServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/yada")
class TodoController {

    private static Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private YadaServices yadaServices;

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

        return ResponseEntity.ok(
                yadaServices
                        .get()
                        .peek(todo -> todo.add(linkTo(methodOn(TodoController.class).getSome()).slash(todo.id).withSelfRel()))
                        .collect(Collectors.toList()));
    }

    @PostMapping(
            path = "/todos",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    HttpEntity<?> create(@RequestBody Todo.CreateTodo createTodo,
                         Authentication authentication) {
        logger.info("create {}", createTodo);
        return ResponseEntity.created(yadaServices.create(createTodo, authentication.getName())).build();
    }

    @PutMapping(
            path = "/todos/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    HttpEntity<?> update(@RequestBody Todo.UpdateTodo updateTodo,
                         @PathVariable("id") Integer id,
                         Authentication authentication) {
        logger.info("update {}", updateTodo);
        return yadaServices.update(id, updateTodo, authentication.getName())
                .map(r -> ResponseEntity.accepted().body(r))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping(
            path = "/todos/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    HttpEntity<?> delete(@PathVariable("id") Integer id) {
        logger.info("delete {}", id);
        yadaServices.delete(id);
        return ResponseEntity.accepted().build();
    }

}
