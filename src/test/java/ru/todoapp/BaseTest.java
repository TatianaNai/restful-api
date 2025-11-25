package ru.todoapp;

import retrofit2.Response;
import ru.todoapp.data.entities.Todo;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseTest {

    protected <T> void assertSuccessfulResponse(Response<T> response, int expectedStatusCode) {
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(expectedStatusCode, response.code(), "Expected code: " + expectedStatusCode + " but was: " + response.code())
        );
    }

    protected <T> void assertUnsuccessfulResponse(Response<T> response, int expectedStatusCode) {
        assertAll(
                () -> assertFalse(response.isSuccessful(), "Request was successful"),
                () -> assertEquals(expectedStatusCode, response.code(), "Expected code: " + expectedStatusCode + " but was: " + response.code())
        );
    }

    protected void assertIfExistTodoInDBAndAPIResponse(List<TodoResponse> todosApi, Todo todoDB, TodoRequest expectedTodo) {
        assertAll(
                () -> assertEquals(expectedTodo.getText(), todoDB.getText(), "Todo's text from DB: " + todoDB.getText() + "is not equal to: " + expectedTodo.getText()),
                () -> assertEquals(expectedTodo.getCompleted(), todoDB.getCompleted(), "Todo's status from DB: " + todoDB.getCompleted() + "is not equal to: " + expectedTodo.getCompleted()),
                () -> assertTrue(todosApi.stream().anyMatch(t ->
                        t.getText().equals(expectedTodo.getText()) &&
                                t.getCompleted().equals(expectedTodo.getCompleted())), "Todo was not updated")
        );
    }
}
