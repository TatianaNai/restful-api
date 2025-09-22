package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.services.TodoRestService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Slf4j
public class ContractTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Verify JSON schema for todo list")
    public void shouldBeCorrectGetTodoResponseScheme() {
        log.info("Check JSON contract");
        todoRestService.getTodosResponse(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("todoResponseSchema.json"));
    }
}
