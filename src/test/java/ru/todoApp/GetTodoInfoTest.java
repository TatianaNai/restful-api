package ru.todoApp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.RandomGenerator;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GetTodoInfoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    static Stream<Arguments> parameterProvider() {
        int totalTodos = new TodoRestService().getAllResponse(HttpStatus.SC_OK)
                .extract().jsonPath().getList("$").size();
        return Stream.of(
                Arguments.of(RandomGenerator.getRandomIntByBorders(-100, 0), HttpStatus.SC_BAD_REQUEST),
                Arguments.of(0, HttpStatus.SC_OK),
                Arguments.of(RandomGenerator.getRandomIntByBorders(1, totalTodos), HttpStatus.SC_OK),
                Arguments.of(totalTodos, HttpStatus.SC_OK),
                Arguments.of(RandomGenerator.getRandomIntByBorders(totalTodos + 1, 100), HttpStatus.SC_OK)
        );
    }

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        todoRestService.getAllResponse(HttpStatus.SC_OK)
                .body("$", everyItem(allOf(
                        hasKey("id"), hasKey("text"), hasKey("completed"))));
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Get list of todos with parameter offset")
    public void shouldHaveCorrectGetTodoListWithOffset(int offset, int statusCode) {
        int amountOfAllTodo = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK).size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        if(offset > 0) {
            int expectedAmountOfTodo = Math.max(amountOfAllTodo - offset, 0);
            log.info("Expected amount of todo in response with offset parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithOffset = todoRestService
                    .getListByType("$", TodoModel.class, statusCode, Map.of("offset", offset))
                    .size();
            log.info("Amount of todo in response with offset parameter: {}", amountOfTodoWithOffset);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithOffset, "Amount of todos: " + amountOfTodoWithOffset + " is not equal to expected amount: " + expectedAmountOfTodo);
        }
        else {
            todoRestService.getAllResponse(statusCode, Map.of("offset", offset));
        }
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Get list of todos with parameter limit")
    public void shouldHaveCorrectGetTodoListWithLimit(int limit, int statusCode) {
        int amountOfAllTodo = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK).size();
        log.info("Total amount of todo: {}", amountOfAllTodo);
        if(limit > 0) {
            int expectedAmountOfTodo = Math.min(limit, amountOfAllTodo);
            log.info("Expected amount of todo in response with limit parameter: {}", expectedAmountOfTodo);
            int amountOfTodoWithLimit = todoRestService
                    .getListByType("$", TodoModel.class, statusCode, Map.of("limit", limit))
                    .size();
            log.info("Amount of todo in response with limit parameter: {}", amountOfTodoWithLimit);
            assertEquals(expectedAmountOfTodo, amountOfTodoWithLimit, "Amount of todos: " + amountOfTodoWithLimit + " is not equal to expected amount: " + expectedAmountOfTodo);
        }
        else {
            todoRestService.getAllResponse(statusCode, Map.of("limit", limit));
        }
    }
}
