package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.services.TodoService;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.todoapp.utils.RandomGenerator.randomIntWithBorders;

@Slf4j
public class ContractTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();
    private final TodoService todoService = new TodoService();

    @Test
    @DisplayName("Verify JSON schema for todo list")
    public void shouldBeCorrectGetTodoResponseScheme() {
        List<Long> todoIds = todoService.createTodosWithAmount(randomIntWithBorders(5, 11));

        log.info("Check JSON contract");
        todoRestService.getTodosResponse(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("todoResponseSchema.json"));

        removeIdsByList(todoIds);
    }
}
