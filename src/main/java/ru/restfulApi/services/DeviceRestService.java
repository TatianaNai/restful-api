package ru.restfulApi.services;

import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.specifications.DefaultSpecification;

import static io.restassured.RestAssured.given;

public class DeviceRestService {
    public static DeviceModel getDeviceById(String id) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .extract()
                .body()
                .as(DeviceModel.class);
    }
}
