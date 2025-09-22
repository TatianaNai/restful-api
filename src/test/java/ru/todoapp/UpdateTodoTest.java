package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.Props.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class UpdateTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Update existing todo")
    public void shouldHaveCorrectUpdateExistingTodo() {
        log.info("Add todo");
        long todoId = generateId();
        TodoModel todo = new TodoModel(todoId,
                randomStringWithLength(getIntProperty("textLength")),
                randomBoolean());
        todoRestService.post(todo, HttpStatus.SC_CREATED);
        log.info("Todo to update: {}", todo);

        TodoModel updateTodo = new TodoModel(generateId(),
                randomStringWithLength(12),
                randomBoolean());
        log.info("Todo for update: {}", updateTodo);
        todoRestService.putById(updateTodo, todoId, HttpStatus.SC_OK);

        List<TodoModel> todosAfterChanging = todoRestService.getListTodo();
        log.info("Check if todo was updated");
        assertAll(
                () -> assertTrue(todosAfterChanging.contains(updateTodo), "Updated todo :" + updateTodo + " is not in the list of all todos"),
                () -> assertFalse(todosAfterChanging.contains(todo), "Todo before changing: " + todo + " is still in the list of all todos")
        );
        log.info("Delete todo with id {}", todoId);
        removeId(todoId);
    }

    @Test
    @DisplayName("Update not existing todo. Negative test")
    public void shouldNotAllowUpdateNotExistingTodo() {
        TodoModel todo = new TodoModel(generateId(),
                randomStringWithLength(getIntProperty("textLength")),
                randomBoolean());
        log.info("Not existed todo for update: {}", todo);
        todoRestService.putById(todo, todo.getId(), HttpStatus.SC_NOT_FOUND);
    }
}
