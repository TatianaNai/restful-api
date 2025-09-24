package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;
import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

public class GetAllTodosTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
    private final TodoService todoService = new TodoService();

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        List<Long> todoIds = todoService.createTodosWithAmount(randomIntWithBorders(5, 11));

        assertSuccessfulResponse(todoApiService.get(), StatusCodes.OK);

        removeIdsByList(todoIds);
    }
}
