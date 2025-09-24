package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class UpdateTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Update existing todo")
    public void shouldHaveCorrectUpdateExistingTodo() {
        long todoIdBeforeUpdate = generateId();
        TodoModel todoBeforeUpdate = new TodoModel(todoIdBeforeUpdate,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoRestService.post(todoBeforeUpdate, HttpStatus.SC_CREATED);

        long todoIdAfterUpdate = generateId();
        TodoModel todoAfterUpdate = new TodoModel(todoIdAfterUpdate,
                randomStringWithLength(12),
                randomBoolean());
        todoRestService.putById(todoAfterUpdate, todoIdBeforeUpdate, HttpStatus.SC_OK);

        List<TodoModel> todosAfterChanging = todoRestService.getListTodo();
        log.info("Check if todo was updated");
        assertAll(
                () -> assertTrue(todosAfterChanging.contains(todoAfterUpdate), "Todo after update:" + todoAfterUpdate + " is not in the list of all todos"),
                () -> assertFalse(todosAfterChanging.contains(todoBeforeUpdate), "Todo before update: " + todoBeforeUpdate + " is still in the list of all todos")
        );
        removeId(todoIdBeforeUpdate);
        removeId(todoIdAfterUpdate);
    }

    @Test
    @DisplayName("Update not existing todo. Negative test")
    public void shouldNotAllowUpdateNotExistingTodo() {
        TodoModel todo = new TodoModel(generateId(),
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoRestService.putById(todo, todo.getId(), HttpStatus.SC_NOT_FOUND);
    }
}
