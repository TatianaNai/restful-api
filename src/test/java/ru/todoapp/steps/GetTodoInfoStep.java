package ru.todoapp.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import ru.todoapp.services.TodoRestService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class GetTodoInfoStep {
    private final ScenarioContext context;
    private final TodoRestService todoRestService = new TodoRestService();
    private int parameterValue;

    public GetTodoInfoStep(ScenarioContext context) {
        this.context = context;
    }

    @When("I send request to get all todos")
    public void getTodos() {
        context.setResponse(todoRestService.getTodosResponse());
    }

    @When("I send request to get todos with positive parameter {string}")
    public void getTodosWithPositiveOffset(String param) {
        parameterValue = randomIntWithBorders(0, 100);
        context.setResponse(todoRestService.getTodosResponse(Map.of(param, parameterValue)));
    }

    @Then("I check amount of todos in response with parameter {string}")
    public void checkAmountTodosInResponseWithPositiveOffset(String param) {
        int expectedAmountOfTodo;
        switch (param) {
            case "offset" -> expectedAmountOfTodo = Math.max(context.getTodoIds().size() - parameterValue, 0);
            case "limit" -> expectedAmountOfTodo = Math.min(parameterValue, context.getTodoIds().size());
            default -> throw new IllegalArgumentException("Unsupported parameter: " + param);
        }
        log.info("Expected amount of todo in response with {} parameter: {}", param, expectedAmountOfTodo);
        int amountOfTodoWithParam = todoRestService.getListTodoFromResponse(context.getResponse()).size();
        log.info("Amount of todo in response with {} parameter: {}", param, amountOfTodoWithParam);
        assertEquals(expectedAmountOfTodo, amountOfTodoWithParam, "Amount of todos: " + amountOfTodoWithParam + " is not equal to expected amount: " + expectedAmountOfTodo);
    }

    @When("I send request to get todos with negative parameter {string}")
    public void getTodosWithNegativeOffset(String paramName) {
        parameterValue = randomIntWithBorders(-100, 0);
        context.setResponse(todoRestService.getTodosResponse(Map.of(paramName, parameterValue)));
    }
}
