package ru.todoApp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;


public class AddTodoTest extends BaseTest{
    private final TodoRestService todoRestService = new TodoRestService();

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")),
                RandomGenerator.getRandomBoolean());

        todoRestService.post(todo, HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Add todo without id. Negative test")
    public void shouldNotAllowAddTodoWithoutId() {
        TodoModel todo = TodoModel.builder()
                .text(RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")))
                .completed(RandomGenerator.getRandomBoolean())
                .build();

        todoRestService.post(todo, HttpStatus.SC_BAD_REQUEST);
    }
}
