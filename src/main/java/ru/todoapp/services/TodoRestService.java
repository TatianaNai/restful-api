package ru.todoapp.services;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import ru.todoapp.models.TodoModel;
import ru.todoapp.specifications.AuthSpecification;
import ru.todoapp.specifications.DefaultSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodoRestService {

    public ValidatableResponse getTodosResponse(int statusCode) {
        return given()
                .spec(DefaultSpecification.requestSpec())
                .when()
                .get()
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public ValidatableResponse getTodosResponse(int statusCode, Map<String, Integer> queryParams) {
        RequestSpecification request = given().spec(DefaultSpecification.requestSpec());
        queryParams.forEach(request::queryParam);
        return request
                .when()
                .get()
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }

    public List<TodoModel> getListTodo() {
        return getTodosResponse(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("$", TodoModel.class);
    }

    public List<Long> getListId() {
        return getTodosResponse(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("id", Long.class);
    }

    public List<TodoModel> getListTodoWithParameters(int statusCode, Map<String, Integer> queryParams) {
        return getTodosResponse(statusCode, queryParams)
                .extract()
                .jsonPath()
                .getList("$", TodoModel.class);
    }

    public ValidatableResponse post(TodoModel todoModel, int statusCode) {
        return given()
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
                .spec(AuthSpecification.requestSpec())
                .when()
                .delete("/" + id)
                .then()
                .spec(DefaultSpecification.responseSpec(statusCode));
    }
}
