package ru.restfulApi;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.restfulApi.extensions.LoggingExtension;
import ru.restfulApi.utils.Props;

@ExtendWith(LoggingExtension.class)
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
