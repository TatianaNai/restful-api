package ru.todoapp.steps;

import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.todoapp.services.TodoRestService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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
        Assertions.assertEquals(statusCode, context.getResponse().statusCode(), "Status code from response " + context.getResponse().statusCode() + " is not equal to " + statusCode);
    }

    @Then("I receive response with status code {int} and all required parameters")
    public void checkIfTodoContainsRequiredParameters(int statusCode) {
        todoRestService.validateAttributesInResponse(context.getResponse(), statusCode);
    }

    @Then("I check json contract")
    public void checkJsonContract() {
        context.getResponse()
                .then()
                .body(matchesJsonSchemaInClasspath("todoResponseSchema.json"));
    }
}
