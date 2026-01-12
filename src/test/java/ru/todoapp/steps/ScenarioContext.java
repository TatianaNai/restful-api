package ru.todoapp.steps;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import ru.todoapp.models.TodoModel;

import java.util.List;

@Getter
@Setter
public class ScenarioContext {
    private TodoModel todo;
    private long randomId;
    private Response response;
    private List<Long> todoIds;
}
