package ru.todoapp.services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.todoapp.models.TodoModel;
import ru.todoapp.specifications.AuthSpecification;
import ru.todoapp.specifications.DefaultSpecification;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;

@Slf4j
public class TodoRestService {

    public Response getTodosResponse() {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get();
    }

    public Response getTodosResponse(Map<String, Integer> queryParams) {
        RequestSpecification request = given().spec(DefaultSpecification.requestSpec());
        queryParams.forEach(request::queryParam);
        return request
                .when()
                .get();
    }

    public List<TodoModel> getListTodoFromResponse(Response response) {
        return response
                .then()
                .extract()
                .jsonPath()
                .getList("$", TodoModel.class);
    }

    public List<Long> getListId() {
        return getTodosResponse()
                .then()
                .extract()
                .jsonPath()
                .getList("id", Long.class);
    }

    public Response postResponse(TodoModel todoModel) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(todoModel)
                .post();
    }

    public Response deleteResponse(Long id) {
        log.info("Delete todo with id {}", id);
        return given()
                .spec(AuthSpecification.requestSpec())
                .when()
                .delete("/" + id);
    }

    public Response putResponse(TodoModel todoModel, long id) {
        log.info("Update todo with id {}", id);
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .body(todoModel)
                .put("/" + id);
    }

    public void validateResponse(Response response, int statusCode) {
        response
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public void validateAttributesInResponse(Response response, int statusCode) {
        response
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode))
                .body("$", everyItem(allOf(
                        hasKey("id"), hasKey("text"), hasKey("completed"))));
    }

    public void validateJsonContract(Response response, String path) {
        response
                .then()
                .body(matchesJsonSchemaInClasspath(path));
    }
}
