package ru.todoApp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;

import java.util.List;


public abstract class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getStringProperty("baseUri");

        TodoRestService todoRestService = new TodoRestService();
        int amountOfTodo = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK).size();
        if(amountOfTodo == 0) {
            for(int i = 0; i < Props.getIntProperty("startAmountTodo"); i++) {
                TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                        RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")),
                        RandomGenerator.getRandomBoolean());
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
        List<Long> ids = todoRestService.getListByType("id", Long.class,HttpStatus.SC_OK);
        for(long id: ids) {
            todoRestService.deleteById(id, HttpStatus.SC_NO_CONTENT);
        }
    }
}
