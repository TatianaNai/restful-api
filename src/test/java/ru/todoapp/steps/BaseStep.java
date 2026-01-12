package ru.todoapp.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import ru.todoapp.services.TodoIdService;

import static ru.todoapp.utils.Props.getProperty;

public class BaseStep {
    private final ScenarioContext context;

    public BaseStep(ScenarioContext context) {
        this.context = context;
    }

    @Before
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = getProperty("baseUri");
    }

    @After
    public void removeId() {
        TodoIdService.INSTANCE.removeId(context.getRandomId());
    }
}
