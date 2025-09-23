package ru.todoapp.specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.todoapp.endpoints.TodoApiEndPoints;

import static ru.todoapp.utils.Props.getProperty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthSpecification {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setAuth(RestAssured.preemptive().basic(
                        getProperty("login"),
                        getProperty("password")
                ))
                .setContentType(ContentType.JSON)
                .setBasePath(TodoApiEndPoints.TODOS)
                .log(LogDetail.ALL)
                .build();
    }
}
