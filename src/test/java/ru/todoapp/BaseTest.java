package ru.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;
import ru.todoapp.services.TodoIdService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseTest {
    @Autowired
    private TodoIdService todoIdService;

    protected long generateId() {
        return todoIdService.generateId();
    }

    protected void removeId(long id) {
        todoIdService.removeId(id);
    }

    protected void removeIdsByList(List<Long> ids) {
        ids.forEach(this::removeId);
    }

    protected <T> void assertUnsuccessfulResponse(Response<T> response, int expectedStatusCode) {
        assertAll(
                () -> assertFalse(response.isSuccessful(), "Request was successful"),
                () -> assertEquals(expectedStatusCode, response.code(), "Expected code: " + expectedStatusCode + " but was: " + response.code())
        );
    }

    protected <T> void assertSuccessfulResponse(Response<T> response, int expectedStatusCode) {
        assertAll(
                () -> assertTrue(response.isSuccessful(), "Request was not successful"),
                () -> assertEquals(expectedStatusCode, response.code(), "Expected code: " + expectedStatusCode + " but was: " + response.code())
        );
    }
}
