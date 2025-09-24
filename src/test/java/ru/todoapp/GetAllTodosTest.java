package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

public class GetAllTodosTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
    private final TodoService todoService = new TodoService();

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        List<Long> todoIds = todoService.createTodosWithAmount(randomIntWithBorders(5, 11));

        Response<List<Todo>> response =  todoApiService.get();
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(StatusCodes.OK, response.code(), "Expected code: " + StatusCodes.OK + " but was: " + response.code())
        );

        removeIdsByList(todoIds);
    }
}
