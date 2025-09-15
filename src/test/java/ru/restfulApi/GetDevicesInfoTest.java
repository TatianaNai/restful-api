package ru.restfulApi;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.specifications.DefaultSpecification;

public class GetDevicesInfoTest {
    @Test
    public void shouldHaveCorrectGetListDevices() {
        RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responceSpec());
    }

    @ValueSource(ints = {5, 4})
    @ParameterizedTest
    public void shouldHaveCorrectGetDeviceById(int id) {
        RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responceSpec());
    }
}
