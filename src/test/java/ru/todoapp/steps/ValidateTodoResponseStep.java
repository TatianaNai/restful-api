package ru.todoapp.steps;

import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import ru.todoapp.services.TodoRestService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ValidateTodoResponseStep {
    private final ScenarioContext context;
    private final TodoRestService todoRestService = new TodoRestService();

    public ValidateTodoResponseStep(ScenarioContext context) {
        this.context = context;
    }

    @Then("I receive response with status code {int}")
    public void checkResponseStatusCode(int statusCode) {
        todoRestService.validateResponse(context.getResponse(), statusCode);
        assertEquals(statusCode, context.getResponse().statusCode(), "Status code from response " + context.getResponse().statusCode() + " is not equal to " + statusCode);
    }

    @Then("I receive response with status code {int} and todos with all required fields")
    public void checkIfTodoContainsRequiredParameters(int statusCode) {
        todoRestService.validateAttributesInResponse(context.getResponse(), statusCode);
    }

    @Then("I check json contract {string}")
    public void checkJsonContract(String path) {
        todoRestService.validateJsonContract(context.getResponse(), path);
    }
}
