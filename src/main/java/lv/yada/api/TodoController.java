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

        return new ResponseEntity<>(yadaServices.get(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/todos",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Secured({"ROLE_ADMIN"})
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
                .map(r -> ResponseEntity.created(r).build())
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
