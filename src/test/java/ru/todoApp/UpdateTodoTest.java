package ru.todoApp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Update existing todo")
    public void shouldHaveCorrectUpdateExistingTodo() {
        List<TodoModel> todosBeforeChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        TodoModel oldTodo = todosBeforeChanging.get(RandomGenerator.getRandomIntByBorders(0, todosBeforeChanging.size()));

        TodoModel updatedTodo = new TodoModel(RandomGenerator.getRandomLongId(),
                RandomGenerator.getRandomStringByLength(12),
                RandomGenerator.getRandomBoolean());

        todoRestService.putById(updatedTodo, oldTodo.getId(), HttpStatus.SC_OK);

        List<TodoModel> todosAfterChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        assertAll(
                () -> assertTrue(todosAfterChanging.contains(updatedTodo), "Updated todo :" + updatedTodo + " is not in the list of all todos"),
                () -> assertFalse(todosAfterChanging.contains(oldTodo), "Todo before changing: " + oldTodo + " is still in the list of all todos")
        );
    }

    @Test
    @DisplayName("Update not existing todo. Negative test")
    public void shouldNotAllowUpdateNotExistingTodo() {
        TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")),
                RandomGenerator.getRandomBoolean());

        todoRestService.putById(todo, todo.getId(), HttpStatus.SC_NOT_FOUND);
    }
}
