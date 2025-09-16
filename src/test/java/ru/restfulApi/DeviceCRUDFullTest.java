package ru.restfulApi;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.DeviceDataModel;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.services.DeviceRestService;
import ru.restfulApi.specifications.DefaultSpecification;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
public class DeviceCRUDFullTest extends BaseTest {
    static Stream<Arguments> deviceProvider() {
        return Stream.of(
                Arguments.of(DeviceModel.builder()
                                .name("Apple-test")
                                .data(DeviceDataModel.builder()
                                        .color("blue")
                                        .price(33.33)
                                        .capacityGB(2)
                                        .build())
                                .build(),
                        DeviceModel.builder()
                                .name("Apple-test2")
                                .data(DeviceDataModel.builder()
                                        .price(33.33)
                                        .capacityGB(2)
                                        .build())
                                .build()),
                Arguments.of(DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(1999)
                                        .price(33.33)
                                        .cpuModel("model")
                                        .hardDiskSize("123")
                                        .build())
                                .build(),
                        DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(1999)
                                        .cpuModel("modelCPU")
                                        .hardDiskSize("123")
                                        .build())
                                .build())
        );
    }

    @ParameterizedTest
    @MethodSource("deviceProvider")
    public void shouldHaveCorrectFullCRUDDevice(DeviceModel deviceToAdd, DeviceModel deviceToChange) {
        log.info("Test: \"shouldHaveCorrectFullCRUDDevice\"");

        log.info("Creating device");
        String deviceId = RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(deviceToAdd)
                .post(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .extract()
                .path("id");
        log.info("Device with id \"{}\" is created", deviceId);
        DeviceModel deviceBeforeChanging = DeviceRestService.getDeviceById(deviceId);
        log.info("Device before changing info:" + deviceBeforeChanging);

        log.info("Changing device's info by id");
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(deviceToChange)
                .put(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK));
        DeviceModel deviceAfterChanging = DeviceRestService.getDeviceById(deviceId);
        log.info("Device after changing info:" + deviceAfterChanging);
        assertNotEquals(deviceBeforeChanging, deviceAfterChanging, "Device before changing: " + deviceBeforeChanging + " is equal to device after changing: " + deviceAfterChanging);

        log.info("Deleting device by id");
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .delete(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK));

        log.info("Check if device was deleted by id");
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_NOT_FOUND));
    }
}
