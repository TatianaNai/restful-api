package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import retrofit2.Response;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Isolated
@Slf4j
public class GetTodoInfoTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
    private final TodoService todoService = new TodoService();
    private static final int AMOUNT_TODOS = randomIntWithBorders(5, 11);

    static Stream<Arguments> parameterProvider() {
        return Stream.of(
                Arguments.of(randomIntWithBorders(-100, 0)),
                Arguments.of(0),
                Arguments.of(randomIntWithBorders(1, AMOUNT_TODOS)),
                Arguments.of(AMOUNT_TODOS),
                Arguments.of(randomIntWithBorders(AMOUNT_TODOS + 1, 100))
        );
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter offset")
    public void shouldReturnCorrectAmountOfTodosWithOffset(int offset) {
        List<Long> todoIds = todoService.createTodosWithAmount(AMOUNT_TODOS);

        if (offset >= 0) {
            int expectedAmountOfTodo = Math.max(getAmountOfAllTodo() - offset, 0);
            log.info("Expected amount of todo in response with offset parameter: {}", expectedAmountOfTodo);
            assertResponseWithPositiveParameter(todoApiService.getWithOffset(offset), expectedAmountOfTodo);
        } else {
            assertUnsuccessfulResponse(todoApiService.getWithOffset(offset), StatusCodes.BAD_REQUEST);
        }

        removeIdsByList(todoIds);
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter limit")
    public void shouldReturnCorrectAmountOfTodosWithLimit(int limit) {
        List<Long> todoIds = todoService.createTodosWithAmount(AMOUNT_TODOS);

        if (limit >= 0) {
            int expectedAmountOfTodo = Math.min(limit, getAmountOfAllTodo());
            log.info("Expected amount of todo in response with limit parameter: {}", expectedAmountOfTodo);
            assertResponseWithPositiveParameter(todoApiService.getWithLimit(limit), expectedAmountOfTodo);
        } else {
            assertUnsuccessfulResponse(todoApiService.getWithLimit(limit), StatusCodes.BAD_REQUEST);
        }

        removeIdsByList(todoIds);
    }

    private int getAmountOfAllTodo() {
        int amountOfAllTodo = todoApiService.getListTodo().size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        return amountOfAllTodo;
    }

    private void assertResponseWithPositiveParameter(Response<List<Todo>> response, int expectedAmountOfTodo) {
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(StatusCodes.OK, response.code(), "Expected code: " + StatusCodes.OK + " but was: " + response.code()),
                () -> assertNotNull(response.body())
        );
        int amountOfTodoWithParameter = response.body().size();
        log.info("Amount of todo in response with parameter: {}", amountOfTodoWithParameter);
        assertEquals(expectedAmountOfTodo, amountOfTodoWithParameter, "Amount of todos: " + amountOfTodoWithParameter + " is not equal to expected amount: " + expectedAmountOfTodo);
    }
}
