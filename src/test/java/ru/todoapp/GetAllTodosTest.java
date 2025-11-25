package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.mappers.TodoMapper;
import ru.todoapp.rest.constants.StatusCodes;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;
import ru.todoapp.rest.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.todoapp.utils.RandomGenerator.*;

@SpringBootTest
public class GetAllTodosTest extends BaseTest {
    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @Test
    @DisplayName("Get list of all todos")
    public void shouldHaveCorrectGetAllTodoList() {
        int randomNumber = randomIntWithBorders(5, 11);
        List<TodoRequest> todos = todoService.createTodosInAmount(randomNumber);

        assertSuccessfulResponse(todoApiService.get(), StatusCodes.OK);

        List<TodoResponse> todosApi = todoApiService.getListTodo();
        List<TodoResponse> todosFromBD = todoRepository.findAll()
                .stream()
                .map(TodoMapper::entityToResponse)
                .toList();
        assertEquals(todosApi, todosFromBD, "List todos from BD is not equal to list todo from app");

        todos.forEach(todoService::deleteTodo);
    }
}
