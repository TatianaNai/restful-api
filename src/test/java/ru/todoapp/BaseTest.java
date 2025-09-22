package ru.todoapp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import static ru.todoapp.utils.Props.*;


public abstract class BaseTest {

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = getStringProperty("baseUri");
    }
}
