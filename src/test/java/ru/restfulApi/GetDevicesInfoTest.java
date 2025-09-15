package ru.restfulApi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.specifications.DefaultSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;

public class GetDevicesInfoTest extends BaseTest{
    @Test
    public void shouldHaveCorrectGetListDevices() {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responceSpec())
                .body("data[2].'capacity GB'", greaterThan(500));
    }

    @ValueSource(ints = {5, 4})
    @ParameterizedTest
    public void shouldHaveCorrectGetDeviceById(int id) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responceSpec())
                .body("$", hasKey("name"));
    }

    @ParameterizedTest
    @CsvSource({
            "7, Apple MacBook Pro 16",
            "2, 'Apple iPhone 12 Mini, 256GB, Blue'"
    })
    public void shouldHaveCorrectDeviceName(int id, String expectedName) {
        DeviceModel device = given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .extract()
                .body()
                .as(DeviceModel.class);
        Assertions.assertEquals(expectedName, device.getName());
    }
}
