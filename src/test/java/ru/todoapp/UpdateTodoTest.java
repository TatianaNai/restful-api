package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class UpdateTodoTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();

    @Test
    @DisplayName("Update existing todo")
    public void shouldHaveCorrectUpdateExistingTodo() {
        long todoIdBeforeUpdate = generateId();
        Todo todoBeforeUpdate = new Todo(todoIdBeforeUpdate,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoApiService.post(todoBeforeUpdate);

        long todoIdAfterUpdate = generateId();
        Todo todoAfterUpdate = new Todo(todoIdAfterUpdate,
                randomStringWithLength(12),
                randomBoolean());
        assertSuccessfulResponse(todoApiService.put(todoIdBeforeUpdate, todoAfterUpdate), StatusCodes.OK);

        List<Todo> todosAfterChanging = todoApiService.getListTodo();
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
        Todo todo = new Todo(generateId(),
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        assertUnsuccessfulResponse(todoApiService.put(todo.getId(), todo), StatusCodes.NOT_FOUND);
    }
}
