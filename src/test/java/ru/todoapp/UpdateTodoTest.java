package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.rest.constants.StatusCodes;
import ru.todoapp.data.entities.Todo;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;
import ru.todoapp.rest.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
@SpringBootTest
public class UpdateTodoTest extends BaseTest {
    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("Update existing todo")
    public void shouldHaveCorrectUpdateExistingTodo() {
        String text = randomStringWithLength(randomIntWithBorders(5, 100));
        todoService.createTodo(new TodoRequest(text, true));
        Todo todoToUpdate = todoRepository.getByText(text).get(0);
        TodoRequest todoAfterUpdate = new TodoRequest(
                randomStringWithLength(randomIntWithBorders(5, 100)),
                false);

        assertSuccessfulResponse(todoService.updateTodo(todoToUpdate.getId(), todoAfterUpdate), StatusCodes.OK);

        Todo updatedTodoFromDB = todoRepository.findById(todoToUpdate.getId()).get();
        List<TodoResponse> todosApi = todoApiService.getListTodo();
        log.info("Check if todo was updated");

        assertFalse(todosApi.stream().anyMatch(t ->
                t.getText().equals(text) &&
                        t.getCompleted().equals(true)), "Todo was not updated");
        assertIfExistTodoInDBAndAPIResponse(todosApi, updatedTodoFromDB, todoAfterUpdate);

        todoService.deleteTodo(todoAfterUpdate);
    }
}
