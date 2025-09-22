package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoapp.services.TodoRestService;
import ru.todoapp.utils.RandomGenerator;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.todoapp.utils.Props.getIntProperty;
import static ru.todoapp.utils.RandomGenerator.*;

@Isolated
@Slf4j
public class GetTodoInfoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    static Stream<Arguments> parameterProvider() {
        int totalTodos = new TodoRestService().getListTodo().size();
        return Stream.of(
                Arguments.of(randomIntWithBorders(-100, 0), HttpStatus.SC_BAD_REQUEST),
                Arguments.of(0, HttpStatus.SC_OK),
                Arguments.of(randomIntWithBorders(1, totalTodos), HttpStatus.SC_OK),
                Arguments.of(totalTodos, HttpStatus.SC_OK),
                Arguments.of(randomIntWithBorders(totalTodos + 1, 100), HttpStatus.SC_OK)
        );
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter offset")
    public void shouldReturnCorrectAmountOfTodosWithOffset(int offset, int statusCode) {
        log.info("Add todos");
        List<Long> todoIds = generateTodosWithAmount(getIntProperty("startAmountTodo"));

        int amountOfAllTodo = todoRestService.getListTodo().size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        if (offset > 0) {
            int expectedAmountOfTodo = Math.max(amountOfAllTodo - offset, 0);
            log.info("Expected amount of todo in response with offset parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithOffset = todoRestService
                    .getListTodoWithParameters(statusCode, Map.of("offset", offset))
                    .size();
            log.info("Amount of todo in response with offset parameter: {}", amountOfTodoWithOffset);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithOffset, "Amount of todos: " + amountOfTodoWithOffset + " is not equal to expected amount: " + expectedAmountOfTodo);
        } else {
            todoRestService.getTodosResponse(statusCode, Map.of("offset", offset));
        }

        log.info("Delete created todos");
        todoIds.forEach(RandomGenerator::removeId);
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter limit")
    public void shouldReturnCorrectAmountOfTodosWithLimit(int limit, int statusCode) {
        log.info("Add todos");
        List<Long> todoIds = generateTodosWithAmount(getIntProperty("startAmountTodo"));

        int amountOfAllTodo = todoRestService.getListTodo().size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        if (limit > 0) {
            int expectedAmountOfTodo = Math.min(limit, amountOfAllTodo);
            log.info("Expected amount of todo in response with limit parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithLimit = todoRestService
                    .getListTodoWithParameters(statusCode, Map.of("limit", limit))
                    .size();
            log.info("Amount of todo in response with limit parameter: {}", amountOfTodoWithLimit);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithLimit, "Amount of todos: " + amountOfTodoWithLimit + " is not equal to expected amount: " + expectedAmountOfTodo);
        } else {
            todoRestService.getTodosResponse(statusCode, Map.of("limit", limit));
        }

        log.info("Delete created todos");
        todoIds.forEach(RandomGenerator::removeId);
    }
}
