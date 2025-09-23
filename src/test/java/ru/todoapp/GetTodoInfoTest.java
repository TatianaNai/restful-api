package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.services.TodoService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.todoapp.utils.RandomGenerator.*;

@Isolated
@Slf4j
public class GetTodoInfoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();
    private final TodoService todoService = new TodoService();
    private static final int AMOUNT_TODOS = randomIntWithBorders(5, 11);

    static Stream<Arguments> parameterProvider() {
        return Stream.of(
                Arguments.of(randomIntWithBorders(-100, 0), HttpStatus.SC_BAD_REQUEST),
                Arguments.of(0, HttpStatus.SC_OK),
                Arguments.of(randomIntWithBorders(1, AMOUNT_TODOS), HttpStatus.SC_OK),
                Arguments.of(AMOUNT_TODOS, HttpStatus.SC_OK),
                Arguments.of(randomIntWithBorders(AMOUNT_TODOS + 1, 100), HttpStatus.SC_OK)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter offset")
    public void shouldReturnCorrectAmountOfTodosWithOffset(int offset, int statusCode) {
        List<Long> todoIds = todoService.createTodosWithAmount(AMOUNT_TODOS);

        if (offset > 0) {
            int expectedAmountOfTodo = Math.max(getAmountOfAllTodo() - offset, 0);
            log.info("Expected amount of todo in response with offset parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithOffset = todoRestService
                    .getListTodoWithParameters(statusCode, Map.of("offset", offset))
                    .size();
            log.info("Amount of todo in response with offset parameter: {}", amountOfTodoWithOffset);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithOffset, "Amount of todos: " + amountOfTodoWithOffset + " is not equal to expected amount: " + expectedAmountOfTodo);
        } else {
            todoRestService.getTodosResponse(statusCode, Map.of("offset", offset));
        }

        removeIdsByList(todoIds);
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter limit")
    public void shouldReturnCorrectAmountOfTodosWithLimit(int limit, int statusCode) {
        List<Long> todoIds = todoService.createTodosWithAmount(AMOUNT_TODOS);

        if (limit > 0) {
            int expectedAmountOfTodo = Math.min(limit, getAmountOfAllTodo());
            log.info("Expected amount of todo in response with limit parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithLimit = todoRestService
                    .getListTodoWithParameters(statusCode, Map.of("limit", limit))
                    .size();
            log.info("Amount of todo in response with limit parameter: {}", amountOfTodoWithLimit);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithLimit, "Amount of todos: " + amountOfTodoWithLimit + " is not equal to expected amount: " + expectedAmountOfTodo);
        } else {
            todoRestService.getTodosResponse(statusCode, Map.of("limit", limit));
        }

        removeIdsByList(todoIds);
    }

    private int getAmountOfAllTodo() {
        int amountOfAllTodo = todoRestService.getListTodo().size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        return amountOfAllTodo;
    }
}
