package ru.todoapp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import static ru.todoapp.utils.Props.*;
import static ru.todoapp.utils.RandomGenerator.*;


public abstract class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getStringProperty("baseUri");

        TodoRestService todoRestService = new TodoRestService();
        int amountOfTodo = todoRestService.getListTodo().size();
        if (amountOfTodo == 0) {
            for (int i = 0; i < getIntProperty("startAmountTodo"); i++) {
                TodoModel todo = new TodoModel(randomLongId(),
                        randomStringWithLength(getIntProperty("textLength")),
                        randomBoolean());
                todoRestService.post(todo, HttpStatus.SC_CREATED);
            }
        }
    }

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterAll
    public static void tearDown() {
        TodoRestService todoRestService = new TodoRestService();
        todoRestService.getListId().forEach(id -> todoRestService.deleteById(id, HttpStatus.SC_NO_CONTENT));
    }
}
