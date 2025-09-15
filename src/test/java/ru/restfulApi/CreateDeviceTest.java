package ru.restfulApi;

import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.LaptopDataModel;
import ru.restfulApi.models.PhoneDataModel;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.specifications.DefaultSpecification;

import java.util.stream.Stream;

public class CreateDeviceTest {
    static Stream<DeviceModel<?>> deviceProvider() {
        DeviceModel<PhoneDataModel> phone = DeviceModel.<PhoneDataModel>builder()
                .name("Apple-test")
                .data(PhoneDataModel.builder()
                        .color("blue")
                        .price(33.33)
                        .capacityGB(2)
                        .build())
                .build();

        DeviceModel<PhoneDataModel> phoneWithoutData = DeviceModel.<PhoneDataModel>builder()
                .name("Apple-test")
                .build();

        DeviceModel<LaptopDataModel> laptop = DeviceModel.<LaptopDataModel>builder()
                .name("Laptop-test")
                .data(LaptopDataModel.builder()
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
    public void shouldHaveCorrectCreateDevice(DeviceModel<?> device) {
        RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .body(device)
                .when()
                .post(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responceSpec());
    }
}
