package ru.todoapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import ru.todoapp.data.entities.Todo;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.mappers.TodoMapper;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.services.TodoApiServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.todoapp.utils.RandomGenerator.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoApiServiceImpl todoApiService;
    private final TodoRepository todoRepository;

    public Response<Void> createTodo(TodoRequest todo) {
        Todo todoToCreate = TodoMapper.todoRequestToEntity(todo);
        todoRepository.save(todoToCreate);
        log.info("Add todo with id {}", todoToCreate.getId());
        return todoApiService.post(TodoMapper.entityToResponse(todoToCreate));
    }

    public List<TodoRequest> createTodosInAmount(int amount) {
        List<TodoRequest> todos = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            TodoRequest todoToAdd = new TodoRequest(
                    randomStringWithLength(randomIntWithBorders(5, 100)),
                    randomBoolean());
            createTodo(todoToAdd);
            todos.add(todoToAdd);
        }
        return todos;
    }

    public Response<Void> updateTodo(Long id, TodoRequest todoToUpdate) {
        log.info("Update todo with id: {}", id);
        Todo todo;
        Optional<Todo> todoOpt= todoRepository.findById(id);
        if(todoOpt.isPresent()) {
            todo = todoOpt.get();
        } else {
            throw new RuntimeException("Todo with id: " + id + " does not exist in DB");
        }

        todo.setText(todoToUpdate.getText());
        todo.setCompleted(todoToUpdate.getCompleted());
        todoRepository.save(todo);
        return todoApiService.put(id, TodoMapper.entityToResponse(todo));
    }

    public Response<Void> deleteTodo(TodoRequest todo) {
        long id = todoRepository
                .getByText(todo.getText())
                .get(0)
                .getId();
        log.info("Delete todo with id: {}", id);
        todoRepository.deleteById(id);
        return todoApiService.delete(id);
    }
}
