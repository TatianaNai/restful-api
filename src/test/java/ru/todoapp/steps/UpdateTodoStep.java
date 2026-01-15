package ru.todoapp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

public class UpdateTodoStep {
    private final ScenarioContext context;
    private final TodoRestService todoRestService = new TodoRestService();
    private TodoModel updatedTodo;

    public UpdateTodoStep(ScenarioContext context) {
        this.context = context;
    }

    @Given("I prepare todo to replace existing todo")
    public void createTodoForUpdate() {
        updatedTodo = new TodoModel(context.getRandomId(),
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
    }

    @When("I send request to update todo in the app")
    public void updateTodo() {
        context.setResponse(todoRestService.putResponse(updatedTodo, context.getTodo().getId()));
    }

    @Then("I check if todo is updated")
    public void checkIfTodoIsUpdated() {
        context.setResponse(todoRestService.getTodosResponse());
        List<TodoModel> todosAfterChanging = todoRestService.getListTodoFromResponse(context.getResponse());
        assertAll(
                () -> assertTrue(todosAfterChanging.contains(updatedTodo), "Todo after update:" + updatedTodo + " is not in the list of all todos"),
                () -> assertFalse(todosAfterChanging.contains(context.getTodo()), "Todo before update: " + context.getTodo() + " is still in the list of all todos")
        );
    }
}
