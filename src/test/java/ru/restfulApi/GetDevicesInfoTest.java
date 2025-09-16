package ru.restfulApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.services.DeviceRestService;
import ru.restfulApi.specifications.DefaultSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GetDevicesInfoTest extends BaseTest{
    @Test
    public void shouldHaveCorrectGetListDevices() {
        log.info("Test: \"shouldHaveCorrectGetListDevices\"");

        log.info("Getting all devices");
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .body("data[2].'capacity GB'", greaterThan(500));
    }

    @ValueSource(ints = {5, 4})
    @ParameterizedTest
    public void shouldHaveCorrectGetDeviceById(int id) {
        log.info("Test: \"shouldHaveCorrectGetDeviceById\"");

        log.info("Getting device by id");
        log.info("Device's id: " + id);
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .body("$", hasKey("name"));
    }

    @ParameterizedTest
    @CsvSource({
            "7, Apple MacBook Pro 16",
            "2, 'Apple iPhone 12 Mini, 256GB, Blue'"
    })
    public void shouldHaveCorrectGetDeviceNameById(String id, String expectedName) {
        log.info("Test: \"shouldHaveCorrectGetDeviceNameById\"");

        log.info("Getting device's name by id");
        String deviceName = DeviceRestService.getDeviceById(id).getName();
        log.info("Device's name: " + deviceName);
        assertEquals(expectedName, deviceName, "Device's name " + deviceName + " is not equal to " + expectedName);
    }
}
