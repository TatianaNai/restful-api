package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.Props.getIntProperty;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class DeleteTodoTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        log.info("Add todo");
        long todoId = generateId();
        TodoModel todo = new TodoModel(todoId,
                randomStringWithLength(getIntProperty("textLength")),
                randomBoolean());
        todoRestService.post(todo, HttpStatus.SC_CREATED);

        log.info("Delete todo by id: {}", todoId);
        todoRestService.deleteById(todoId, HttpStatus.SC_NO_CONTENT);

        List<TodoModel> todosAfterChanging = todoRestService.getListTodo();
        log.info("Check if todo is deleted");
        assertFalse(todosAfterChanging.contains(todo), "Todo before deleting: " + todo + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        long randomId = generateId();
        log.info("Random id to delete not existing todo: {}", randomId);
        todoRestService.deleteById(randomId, HttpStatus.SC_NOT_FOUND);
    }
}
