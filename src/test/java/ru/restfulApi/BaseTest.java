package ru.restfulApi;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import ru.restfulApi.utils.Props;

public abstract class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getProperty("baseUri");
    }
}
