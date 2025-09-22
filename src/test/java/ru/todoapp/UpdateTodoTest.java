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
        List<TodoModel> todosBeforeChanging = todoRestService.getListTodo();
        TodoModel oldTodo = todosBeforeChanging.get(randomIntWithBorders(0, todosBeforeChanging.size()));
        log.info("Todo to update: {}", oldTodo);
        TodoModel updateTodo = new TodoModel(randomLongId(),
                randomStringWithLength(12),
                randomBoolean());
        log.info("Todo for update: {}", updateTodo);
        todoRestService.putById(updateTodo, oldTodo.getId(), HttpStatus.SC_OK);

        List<TodoModel> todosAfterChanging = todoRestService.getListTodo();
        log.info("Check if todo was updated");
        assertAll(
                () -> assertTrue(todosAfterChanging.contains(updateTodo), "Updated todo :" + updateTodo + " is not in the list of all todos"),
                () -> assertFalse(todosAfterChanging.contains(oldTodo), "Todo before changing: " + oldTodo + " is still in the list of all todos")
        );
    }

    @Test
    @DisplayName("Update not existing todo. Negative test")
    public void shouldNotAllowUpdateNotExistingTodo() {
        TodoModel todo = new TodoModel(randomLongId(),
                randomStringWithLength(getIntProperty("textLength")),
                randomBoolean());
        log.info("Not existed todo for update: {}", todo);
        todoRestService.putById(todo, todo.getId(), HttpStatus.SC_NOT_FOUND);
    }
}
