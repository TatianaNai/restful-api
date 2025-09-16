package ru.restfulApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.specifications.DefaultSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

@Slf4j
public class ContractTest extends BaseTest {
    @Test
    public void shouldBeCorrectGetDevicesResponseScheme() {
        log.info("Test: \"shouldBeCorrectGetDevicesResponseScheme\"");

        log.info("Check JSON contract");
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .body(matchesJsonSchemaInClasspath("deviceResponseSchema.json"));
    }
}
