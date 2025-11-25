package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.rest.constants.StatusCodes;
import ru.todoapp.data.entities.Todo;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;
import ru.todoapp.rest.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
@SpringBootTest
public class DeleteTodoTest extends BaseTest {

    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        TodoRequest todo = new TodoRequest(
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoService.createTodo(todo);

        assertSuccessfulResponse(todoService.deleteTodo(todo), StatusCodes.NO_CONTENT);

        log.info("Check if todo is deleted");
        List<TodoResponse> todosApi = todoApiService.getListTodo();
        List<Todo> todosFromBD = todoRepository.findAll();

        assertAll(
                () -> assertFalse(todosApi.stream().anyMatch(t ->
                        t.getText().equals(todo.getText()) &&
                                t.getCompleted().equals(todo.getCompleted())), "Todo was not added: " + todo),
                () -> assertFalse(todosFromBD.stream().anyMatch(t ->
                        t.getText().equals(todo.getText()) &&
                                t.getCompleted().equals(todo.getCompleted())), "Todo was not added: " + todo)
        );
    }
}
