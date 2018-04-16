package lv.yada.services;

import lv.yada.model.Todo;
import lv.yada.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public interface YadaServices {
    Optional<URI> update(Integer id,
                         Todo.UpdateTodo updateTodo,
                         String userName);

    URI create(Todo.CreateTodo createTodo,
               String userName);

    List<Todo> get();
}

@Service
class YadaServicesImpl
        implements YadaServices {
    @Autowired
    private TodoRepo todoRepo;

    @Override
    public Optional<URI> update(Integer id,
                                Todo.UpdateTodo updateTodo,
                                String userName) {
        Optional<Todo> maybeTodo = todoRepo.findById(id).map(todo -> todo.forUpdate(updateTodo, userName));
        return maybeTodo.map(todo -> ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(todoRepo.save(todo))
                .toUri());
    }

    @Override
    public URI create(Todo.CreateTodo createTodo, String userName) {
        Todo todo1 = createTodo.withCreator(userName);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(todoRepo.save(todo1))
                .toUri();
    }

    @Override
    public List<Todo> get() {
        return todoRepo.findAll().stream()
                .peek(todo -> todo.add(linkTo(methodOn(YadaServices.class).get()).withSelfRel()))
                .collect(Collectors.toList());
    }
}