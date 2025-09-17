package ru.restfulApi.services;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import ru.restfulApi.endPoints.RestfulApiEndPoints;
import ru.restfulApi.models.DeviceModel;
import ru.restfulApi.specifications.DefaultSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeviceRestService {
    public DeviceModel getDeviceById(String id) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .extract()
                .body()
                .as(DeviceModel.class);
    }

    public ValidatableResponse addDeviceResponse(DeviceModel device, int statusCode) {
        return RestAssured.given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(device)
                .post(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public ValidatableResponse getDeviceResponse(String id, int statusCode) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public ValidatableResponse getAllDevicesResponse(int statusCode) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get(RestfulApiEndPoints.devices)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void deleteDeviceById(String id, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .delete(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void putDeviceById(DeviceModel device, String id, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(device)
                .put(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void patchDeviceById(Map<String, String> parameter, String id, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(parameter)
                .patch(RestfulApiEndPoints.deviceById, id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }
}
