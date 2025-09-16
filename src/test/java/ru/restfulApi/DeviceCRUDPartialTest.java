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

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
public class DeviceCRUDPartialTest extends BaseTest {
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
                        Map.of("name", "Change name")),
                Arguments.of(DeviceModel.builder()
                        .name("Apple-second-test")
                        .build(),
                        Map.of("name", "Apple phone")),
                Arguments.of(DeviceModel.builder()
                                .name("Laptop-test")
                                .data(DeviceDataModel.builder()
                                        .year(1999)
                                        .price(33.33)
                                        .cpuModel("model")
                                        .hardDiskSize("123")
                                        .build())
                                .build(),
                        Map.of("name", "Laptop"))
        );
    }

    @ParameterizedTest
    @MethodSource("deviceProvider")
    public void shouldHaveCorrectPartialCRUDDevice(DeviceModel device, Map<String, String> parameter) {
        String deviceId = RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(device)
                .post(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK))
                .extract()
                .path("id");
        log.info("Device with id \"{}\" is created", deviceId);

        String nameBeforeChanging = DeviceRestService.getDeviceById(deviceId).getName();

        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(parameter)
                .patch(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK));

        String nameAfterChanging = DeviceRestService.getDeviceById(deviceId).getName();

        assertNotEquals(nameBeforeChanging, nameAfterChanging);
        assertEquals(parameter.values().iterator().next(), nameAfterChanging);

        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .delete(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_OK));

        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, deviceId)
                .then()
                .spec(DefaultSpecification.responseSpec(HttpStatus.SC_NOT_FOUND));
    }
}
