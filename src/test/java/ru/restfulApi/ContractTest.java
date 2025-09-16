package ru.restfulApi;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.specifications.DefaultSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class ContractTest extends BaseTest {
    @Test
    public void shouldBeCorrectGetDevicesResponseScheme() {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .body(matchesJsonSchemaInClasspath("deviceResponseSchema.json"));
    }
}
