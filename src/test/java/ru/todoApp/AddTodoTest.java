package ru.todoApp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;

import java.util.stream.Stream;


public class AddTodoTest extends BaseTest{
    private final TodoRestService todoRestService = new TodoRestService();

    static Stream<Arguments> incorrectTodoProvider() {
        return Stream.of(
                Arguments.of(TodoModel.builder()
                        .text(RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")))
                        .completed(RandomGenerator.getRandomBoolean())
                        .build()),
                Arguments.of(TodoModel.builder()
                        .id(RandomGenerator.getRandomLongId())
                        .completed(RandomGenerator.getRandomBoolean())
                        .build()),
                Arguments.of(TodoModel.builder()
                        .id(RandomGenerator.getRandomLongId())
                        .text(RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")))
                        .build())
        );
    }

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")),
                RandomGenerator.getRandomBoolean());
        todoRestService.post(todo, HttpStatus.SC_CREATED);
    }

    @ParameterizedTest
    @MethodSource("incorrectTodoProvider")
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(TodoModel todo) {
        todoRestService.post(todo, HttpStatus.SC_BAD_REQUEST);
    }
}
