package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
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
        Response<Void> response =  todoApiService.put(todoIdBeforeUpdate, todoAfterUpdate);
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(StatusCodes.OK, response.code(), "Expected code: " + StatusCodes.OK + " but was: " + response.code())
        );

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
        Response<Void> response =  todoApiService.put(todo.getId(), todo);
        assertAll(
                () -> assertFalse(response.isSuccessful(), "Request was successful"),
                () -> assertEquals(StatusCodes.NOT_FOUND, response.code(), "Expected code: " + StatusCodes.NOT_FOUND + " but was: " + response.code())
        );
    }
}
