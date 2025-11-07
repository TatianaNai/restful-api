package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class DeleteTodoTest extends BaseTest {
    private final TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();

    @Test
    @DisplayName("Delete existing todo")
    public void shouldHaveCorrectDeleteExistingTodo() {
        long todoId = generateId();
        Todo todo = new Todo(todoId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());
        todoApiService.post(todo);

        assertSuccessfulResponse(todoApiService.delete(todoId), StatusCodes.NO_CONTENT);

        log.info("Check if todo is deleted");
        List<Todo> todosAfterChanging = todoApiService.getListTodo();
        assertFalse(todosAfterChanging.contains(todo), "Todo before deleting: " + todo + " is still in the list of all todos");
    }

    @Test
    @DisplayName("Delete not existing todo. Negative test")
    public void shouldNotAllowDeleteNotExistingTodo() {
        assertUnsuccessfulResponse(todoApiService.delete(generateId()), StatusCodes.NOT_FOUND);
    }
}
