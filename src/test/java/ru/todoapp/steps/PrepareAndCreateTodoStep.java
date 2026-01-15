package ru.todoapp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoIdService;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.services.TodoService;

import static ru.todoapp.utils.RandomGenerator.randomBoolean;
import static ru.todoapp.utils.RandomGenerator.randomIntWithBorders;
import static ru.todoapp.utils.RandomGenerator.randomStringWithLength;

public class PrepareAndCreateTodoStep {
    private final ScenarioContext context;
    private final TodoRestService todoRestService = new TodoRestService();
    private final TodoService todoService = new TodoService();

    public PrepareAndCreateTodoStep(ScenarioContext context) {
        this.context = context;
    }

    @Given("I prepare todo with correct fields")
    public void createTodo() {
        context.setRandomId(TodoIdService.INSTANCE.generateId());
        context.setTodo(new TodoModel(context.getRandomId(),
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean()));
    }

    @Given("I prepare todo without field id")
    public void createTodoWithoutId() {
        context.setRandomId(TodoIdService.INSTANCE.generateId());
        context.setTodo(TodoModel.builder()
                .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                .completed(randomBoolean())
                .build());
    }

    @Given("I prepare todo without field text")
    public void createTodoWithoutText() {
        context.setRandomId(TodoIdService.INSTANCE.generateId());
        context.setTodo(TodoModel.builder()
                .id(TodoIdService.INSTANCE.generateId())
                .completed(randomBoolean())
                .build());
    }

    @Given("I prepare todo without field completed")
    public void createTodoWithoutCompleted() {
        context.setRandomId(TodoIdService.INSTANCE.generateId());
        context.setTodo(TodoModel.builder()
                .id(TodoIdService.INSTANCE.generateId())
                .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                .build());
    }

    @When("I send request to add todo to the app")
    public void postTodo() {
        context.setResponse(todoRestService.postResponse(context.getTodo()));
    }

    @Given("I prepare todos and send request to add todos to the app")
    public void createTodos() {
        context.setTodoIds(todoService.createTodosWithAmount(randomIntWithBorders(5, 11)));
    }
}
