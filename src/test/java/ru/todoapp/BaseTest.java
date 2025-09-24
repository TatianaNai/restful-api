package ru.todoapp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import ru.todoapp.services.TodoIdService;

import java.util.List;

import static ru.todoapp.utils.Props.*;


public abstract class BaseTest {

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = getProperty("baseUri")
                .replace("${port}", getProperty("port"));
    }

    public long generateId() {
        return TodoIdService.INSTANCE.generateId();
    }

    public void removeId(long id) {
        TodoIdService.INSTANCE.removeId(id);
    }

    public void removeIdsByList(List<Long> ids) {
        ids.forEach(this::removeId);
    }
}
