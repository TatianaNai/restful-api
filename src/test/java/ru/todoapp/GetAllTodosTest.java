package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;
import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

@SpringBootTest
public class GetAllTodosTest extends BaseTest {
    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        List<Long> todoIds = todoService.createTodosWithAmount(randomIntWithBorders(5, 11));

        assertSuccessfulResponse(todoApiService.get(), StatusCodes.OK);

        removeIdsByList(todoIds);
    }
}
