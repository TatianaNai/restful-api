package ru.todoapp.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoIdService;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
public class DeleteTodoStep {
    private final ScenarioContext context;
    private final TodoRestService todoRestService = new TodoRestService();

    public DeleteTodoStep(ScenarioContext context) {
        this.context = context;
    }

    @Then("I delete all todos")
    public void deleteAllTodos() {
        context.getTodoIds().forEach(TodoIdService.INSTANCE::removeId);
    }

    @When("I send request to delete new todo in the app")
    public void deleteTodo() {
        context.setResponse(todoRestService.deleteResponse(context.getTodo().getId()));
    }

    @Then("I check if the app does not contain deleted todo")
    public void checkIfTodoIsDeleted() {
        log.info("Check if todo is deleted");
        context.setResponse(todoRestService.getTodosResponse());
        List<TodoModel> todosAfterChanging = todoRestService.getListTodoFromResponse(context.getResponse());
        assertFalse(todosAfterChanging.contains(context.getTodo()), "Todo before deleting: " + context.getTodo() + " is still in the list of all todos");
    }
}
