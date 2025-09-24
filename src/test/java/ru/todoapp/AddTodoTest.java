package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import retrofit2.Response;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoIdService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

public class AddTodoTest extends BaseTest{
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
        long randomId = generateId();
        Todo todo = new Todo(randomId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        Response<Void> response =  todoApiService.post(todo);
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(StatusCodes.CREATED, response.code(), "Expected code: " + StatusCodes.CREATED + " but was: " + response.code())
        );

        removeId(randomId);
    }

    @ParameterizedTest
    @MethodSource("incorrectTodoProvider")
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(Todo todo) {
        Response<Void> response =  todoApiService.post(todo);
        assertAll(
                () -> assertFalse(response.isSuccessful(), "Request was successful"),
                () -> assertEquals(StatusCodes.BAD_REQUEST, response.code(), "Expected code: " + StatusCodes.BAD_REQUEST + " but was: " + response.code())
        );
    }
}
