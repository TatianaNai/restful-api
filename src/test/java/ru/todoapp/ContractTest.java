package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.utils.RandomGenerator;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.todoapp.utils.Props.getIntProperty;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class ContractTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Verify JSON schema for todo list")
    public void shouldBeCorrectGetTodoResponseScheme() {
        log.info("Add todos");
        List<Long> todoIds = generateTodosWithAmount(getIntProperty("amountTodos"));

        log.info("Check JSON contract");
        todoRestService.getTodosResponse(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("todoResponseSchema.json"));

        log.info("Delete created todos");
        todoIds.forEach(RandomGenerator::removeId);
    }
}
