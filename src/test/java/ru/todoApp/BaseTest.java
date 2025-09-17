package ru.todoApp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.todoApp.utils.Props;

public abstract class BaseTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getProperty("baseUri");
    }

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }
}
