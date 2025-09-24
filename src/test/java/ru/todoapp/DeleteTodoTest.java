package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class DeleteTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        long todoId = generateId();
        TodoModel todo = new TodoModel(todoId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoRestService.post(todo, HttpStatus.SC_CREATED);

        todoRestService.deleteById(todoId, HttpStatus.SC_NO_CONTENT);

        log.info("Check if todo is deleted");
        List<TodoModel> todosAfterChanging = todoRestService.getListTodo();
        assertFalse(todosAfterChanging.contains(todo), "Todo before deleting: " + todo + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        todoRestService.deleteById(generateId(), HttpStatus.SC_NOT_FOUND);
    }
}
