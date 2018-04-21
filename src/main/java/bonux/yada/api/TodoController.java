package bonux.yada.api;

import bonux.yada.model.Todo;
import bonux.yada.services.YadaServices;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    @Secured({"ADMIN", "USER"})
    HttpEntity<?> getSome() {
        logger.info("getSome");

        return ResponseEntity.ok(
                yadaServices
                        .get()
                        .peek(todo -> todo.add(linkTo(methodOn(TodoController.class).getSome()).slash(todo.id).withSelfRel()))
                        .collect(Collectors.toList()));
    }

    @GetMapping(
            path = "/todos/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({"ADMIN", "USER"})
    HttpEntity<?> getOne(@PathVariable("id") Integer id) {
        logger.info("getOne {}", id);

        return yadaServices
                .get(id)
                .map(todo -> {
                    todo.add(linkTo(methodOn(TodoController.class).getSome()).slash(todo.id).withSelfRel());
                    return ResponseEntity.ok(todo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(
            path = "/todos",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ADMIN")
    HttpEntity<?> create(@RequestBody Todo.CreateTodo createTodo,
                         Authentication authentication) {
        logger.info("create {}", createTodo);
        return ResponseEntity
                .created(toUri(yadaServices.create(createTodo, authentication.getName())))
                .build();
    }

    @PutMapping(
            path = "/todos/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured({"ADMIN", "USER"})
    HttpEntity<?> update(@RequestBody Todo.UpdateTodo updateTodo,
                         @PathVariable("id") Integer id,
                         Authentication authentication) {
        logger.info("update {}", updateTodo);

        return yadaServices.update(id, updateTodo, authentication.getName())
                .map(r -> ResponseEntity.accepted().body(toUri(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    private URI toUri(Todo r) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(r)
                .toUri();
    }

    @DeleteMapping(
            path = "/todos/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    @Secured("ADMIN")
    HttpEntity<?> delete(@PathVariable("id") Integer id) {
        logger.info("delete {}", id);
        yadaServices.delete(id);
        return ResponseEntity.accepted().build();
    }

}
