package ru.todoApp;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.RandomGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        List<TodoModel> todosBeforeChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        assertFalse(todosBeforeChanging.isEmpty(), "Todos list is empty");

        TodoModel todoToDelete = todosBeforeChanging.get(RandomGenerator.getRandomIntByBorders(0, todosBeforeChanging.size()));

        todoRestService.deleteById(todoToDelete.getId(), HttpStatus.SC_NO_CONTENT);

        List<TodoModel> todosAfterChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        assertFalse(todosAfterChanging.contains(todoToDelete), "Todo before deleting: " + todoToDelete + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        todoRestService.deleteById(RandomGenerator.getRandomLongId(), HttpStatus.SC_NOT_FOUND);
    }
}
