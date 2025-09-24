package ru.todoapp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.services.TodoService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static ru.todoapp.utils.RandomGenerator.randomIntWithBorders;

public class GetAllTodosTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();
    private final TodoService todoService = new TodoService();

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        List<Long> todoIds = todoService.createTodosWithAmount(randomIntWithBorders(5, 11));

        todoRestService.getTodosResponse(HttpStatus.SC_OK)
                .body("$", everyItem(allOf(
                        hasKey("id"), hasKey("text"), hasKey("completed"))));

        removeIdsByList(todoIds);
    }
}
