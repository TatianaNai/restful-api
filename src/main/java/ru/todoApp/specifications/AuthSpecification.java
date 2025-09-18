package ru.todoApp.specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.todoApp.endPoints.TodoApiEndPoints;
import ru.todoApp.utils.Props;

public class AuthSpecification {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setAuth(RestAssured.preemptive().basic(
                        Props.getStringProperty("login"),
                        Props.getStringProperty("password")
                ))
                .setContentType(ContentType.JSON)
                .setBasePath(TodoApiEndPoints.TODOS)
                .log(LogDetail.ALL)
                .build();
    }
}
