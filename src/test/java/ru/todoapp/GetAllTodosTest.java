package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.utils.RandomGenerator;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static ru.todoapp.utils.Props.getIntProperty;
import static ru.todoapp.utils.RandomGenerator.generateTodosWithAmount;

@Slf4j
public class GetAllTodosTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        log.info("Add todos");
        List<Long> todoIds = generateTodosWithAmount(getIntProperty("amountTodos"));

        log.info("Check getting all todos");
        todoRestService.getTodosResponse(HttpStatus.SC_OK)
                .body("$", everyItem(allOf(
                        hasKey("id"), hasKey("text"), hasKey("completed"))));

        log.info("Delete created todos");
        todoIds.forEach(RandomGenerator::removeId);
    }
}
