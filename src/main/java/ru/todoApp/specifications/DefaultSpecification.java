package ru.todoApp.specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.todoApp.endPoints.TodoApiEndPoints;
import ru.todoApp.utils.Props;

public class DefaultSpecification {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Props.getProperty("baseUri"))
                .setAuth(RestAssured.basic(Props.getProperty("login"), Props.getProperty("password")))
                .setContentType(ContentType.JSON)
                .setBasePath(TodoApiEndPoints.TODOS)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(LogDetail.ALL)
                .build();
    }
}
