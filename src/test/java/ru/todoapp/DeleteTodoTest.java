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
public class DeleteTodoTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        long todoId = generateId();
        Todo todo = new Todo(todoId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoApiService.post(todo);

        Response<Void> response =  todoApiService.delete(todoId);
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(StatusCodes.NO_CONTENT, response.code(), "Expected code: " + StatusCodes.NO_CONTENT + " but was: " + response.code())
        );

        log.info("Check if todo is deleted");
        List<Todo> todosAfterChanging = todoApiService.getListTodo();
        assertFalse(todosAfterChanging.contains(todo), "Todo before deleting: " + todo + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        Response<Void> response =  todoApiService.delete(generateId());
        assertAll(
                () -> assertFalse(response.isSuccessful(), "Request was successful"),
                () -> assertEquals(StatusCodes.NOT_FOUND, response.code(), "Expected code: " + StatusCodes.NOT_FOUND + " but was: " + response.code())
        );
    }
}
