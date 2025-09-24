package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoIdService;

import java.util.stream.Stream;

import static ru.todoapp.utils.RandomGenerator.*;

public class AddTodoTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();

    static Stream<Arguments> incorrectTodoProvider() {
        return Stream.of(
                Arguments.of(Todo.builder()
                        .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(Todo.builder()
                        .id(TodoIdService.INSTANCE.generateId())
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(Todo.builder()
                        .id(TodoIdService.INSTANCE.generateId())
                        .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                        .build())
        );
    }

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        long todoId = generateId();
        Todo todo = new Todo(todoId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());

        assertSuccessfulResponse(todoApiService.post(todo), StatusCodes.CREATED);

        removeId(todoId);
    }

    @ParameterizedTest
    @MethodSource("incorrectTodoProvider")
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(Todo todo) {
        assertUnsuccessfulResponse(todoApiService.post(todo), StatusCodes.BAD_REQUEST);
    }
}
