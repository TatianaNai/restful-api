package ru.restfulApi;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.DeviceDataModel;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.specifications.DefaultSpecification;

import java.util.stream.Stream;

@Slf4j
public class CreateDeviceTest extends BaseTest {
    static Stream<DeviceModel> deviceProvider() {
        DeviceModel phone = DeviceModel.builder()
                .name("Apple-test")
                .data(DeviceDataModel.builder()
                        //.color("blue")
                        .price(33.33)
                        //.capacityGB(2)
                        .build())
                .build();

        DeviceModel phoneWithoutData = DeviceModel.builder()
                .name("Apple-test")
                .build();

        DeviceModel laptop = DeviceModel.builder()
                .name("Laptop-test")
                .data(DeviceDataModel.builder()
                        .year(1999)
                        .price(33.33)
                        .cpuModel("model")
                        .hardDiskSize("123")
                        .build())
                .build();

        return Stream.of(phone, phoneWithoutData, laptop);
    }

    @ParameterizedTest
    @MethodSource("deviceProvider")
    public void shouldHaveCorrectCreateDevice(DeviceModel device) {
        RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(device)
                .post(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responceSpec());
    }
}
