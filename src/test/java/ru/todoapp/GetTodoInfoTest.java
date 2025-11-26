package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.rest.constants.StatusCodes;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;
import ru.todoapp.rest.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Isolated
@Slf4j
@SpringBootTest
public class GetTodoInfoTest extends BaseTest {
    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

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
        List<TodoRequest> todos = todoService.createTodosInAmount(AMOUNT_TODOS);

        if (offset >= 0) {
            int expectedAmountOfTodo = Math.max(getAmountOfAllTodo() - offset, 0);
            log.info("Expected amount of todo in response with offset parameter: {}", expectedAmountOfTodo);
            assertResponseWithPositiveParameter(todoApiService.getWithOffset(offset), expectedAmountOfTodo);
        } else {
            assertUnsuccessfulResponse(todoApiService.getWithOffset(offset), StatusCodes.BAD_REQUEST);
        }

        todos.forEach(todoService::deleteTodo);
    }

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @DisplayName("Check amount of todos with parameter limit")
    public void shouldReturnCorrectAmountOfTodosWithLimit(int limit) {
        List<TodoRequest> todos = todoService.createTodosInAmount(AMOUNT_TODOS);

        if (limit >= 0) {
            int expectedAmountOfTodo = Math.min(limit, getAmountOfAllTodo());
            log.info("Expected amount of todo in response with limit parameter: {}", expectedAmountOfTodo);
            assertResponseWithPositiveParameter(todoApiService.getWithLimit(limit), expectedAmountOfTodo);
        } else {
            assertUnsuccessfulResponse(todoApiService.getWithLimit(limit), StatusCodes.BAD_REQUEST);
        }

        todos.forEach(todoService::deleteTodo);
    }

    @DisplayName("Get todos with the same name")
    @Test
    public void shouldHaveCorrectGetTodosWithOneName() {
        String text = randomStringWithLength(randomIntWithBorders(5, 100));
        int randomNumber = randomIntWithBorders(2, 5);
        for (int i = 0; i < randomNumber; i++) {
            todoService.createTodo(new TodoRequest(text, true));
        }
        int amountTodo = todoRepository.getByText(text).size();

        assertEquals(randomNumber, amountTodo);
    }

    private int getAmountOfAllTodo() {
        int amountOfAllTodo = todoApiService.getListTodo().size();
        if (amountOfAllTodo != todoRepository.findAll().size()) {
            throw new RuntimeException("Amount of todo's in DB is not equal to amount todo in app");
        }
        log.info("Total amount of todo: {}", amountOfAllTodo);
        return amountOfAllTodo;
    }

    private void assertResponseWithPositiveParameter(Response<List<TodoResponse>> response, int expectedAmountOfTodo) {
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
