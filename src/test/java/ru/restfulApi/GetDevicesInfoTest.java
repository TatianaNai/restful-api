package ru.restfulApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.utils.Props;

public class GetDevicesInfoTest {
    @Test
    public void shouldHaveCorrectGetListDevices() {
        RestAssured.given()
                .baseUri(Props.getProperty("baseUri"))
                .contentType(ContentType.JSON)
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .statusCode(200);
    }

    @ValueSource(ints = {5, 4})
    @ParameterizedTest
    public void shouldHaveCorrectGetDeviceById(int id) {
        RestAssured.given()
                .baseUri(Props.getProperty("baseUri"))
                .contentType(ContentType.JSON)
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .statusCode(200);
    }
}
