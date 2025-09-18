package ru.todoApp.services;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.todoApp.models.TodoModel;
import ru.todoApp.specifications.DefaultSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodoRestService {

    public ValidatableResponse getAllResponse(int statusCode) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get()
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public ValidatableResponse getAllResponse(int statusCode, Map<String, Integer> queryParams) {
        RequestSpecification request = given().spec(DefaultSpecification.requestSpec());
        queryParams.forEach(request::queryParam);
        return request
                .when()
                .get()
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public <T> List<T> getListByType(String path, Class<T> type, int statusCode) {
        return getAllResponse(statusCode)
                .extract()
                .jsonPath()
                .getList(path, type);
    }

    public <T> List<T> getListByType(String path, Class<T> type, int statusCode, Map<String, Integer> queryParams) {
        return getAllResponse(statusCode, queryParams)
                .extract()
                .jsonPath()
                .getList(path, type);
    }

    public void post(TodoModel todoModel, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(todoModel)
                .post()
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void putById(TodoModel todoModel, long id, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(todoModel)
                .put("/" + id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void deleteById(long id, int statusCode) {
        given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .delete("/" + id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }
}
