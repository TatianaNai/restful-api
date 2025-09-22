package ru.todoapp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.stream.Stream;

import static ru.todoapp.utils.Props.getIntProperty;
import static ru.todoapp.utils.RandomGenerator.*;


public class AddTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    static Stream<Arguments> incorrectTodoProvider() {
        return Stream.of(
                Arguments.of(TodoModel.builder()
                        .text(randomStringWithLength(getIntProperty("textLength")))
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(TodoModel.builder()
                        .id(generateId())
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(TodoModel.builder()
                        .id(generateId())
                        .text(randomStringWithLength(getIntProperty("textLength")))
                        .build())
        );
    }

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        long randomId = generateId();
        TodoModel todo = new TodoModel(randomId,
                randomStringWithLength(getIntProperty("textLength")),
                randomBoolean());
        todoRestService.post(todo, HttpStatus.SC_CREATED);

        removeId(randomId);
    }

    @ParameterizedTest
    @MethodSource("incorrectTodoProvider")
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(TodoModel todo) {
        todoRestService.post(todo, HttpStatus.SC_BAD_REQUEST);
    }
}
