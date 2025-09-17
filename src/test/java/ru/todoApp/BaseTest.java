package ru.todoApp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.todoApp.models.TodoModel;
import ru.todoApp.specifications.DefaultSpecification;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getProperty("baseUri");
        int amountOfTodo = given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get()
                .jsonPath().getList("$").size();

        if(amountOfTodo == 0) {
            for(int i = 0; i < 10; i++) {
                TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                        RandomGenerator.getRandomStringByLength(12),
                        RandomGenerator.getRandomBoolean());

                given()
                        .spec(DefaultSpecification.requestSpec())
                        .when()
                        .body(todo)
                        .post()
                        .then()
                        .spec(DefaultSpecification.responseSpec(HttpStatus.SC_CREATED));
            }
        }

    }

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }
}
