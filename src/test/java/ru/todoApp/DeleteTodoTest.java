package ru.todoApp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.RandomGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class DeleteTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        List<TodoModel> todosBeforeChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        log.info("Check if todo list is not empty");
        assertFalse(todosBeforeChanging.isEmpty(), "Todos list is empty");

        TodoModel todoToDelete = todosBeforeChanging.get(RandomGenerator.getRandomIntByBorders(0, todosBeforeChanging.size()));
        log.info("Delete todo by id: {}", todoToDelete.getId());
        todoRestService.deleteById(todoToDelete.getId(), HttpStatus.SC_NO_CONTENT);

        List<TodoModel> todosAfterChanging = todoRestService.getListByType("$", TodoModel.class,HttpStatus.SC_OK);
        log.info("Check if todo is deleted");
        assertFalse(todosAfterChanging.contains(todoToDelete), "Todo before deleting: " + todoToDelete + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        long randomId = RandomGenerator.getRandomLongId();
        log.info("Random id to delete not existing todo: {}", randomId);
        todoRestService.deleteById(randomId, HttpStatus.SC_NOT_FOUND);
    }
}
