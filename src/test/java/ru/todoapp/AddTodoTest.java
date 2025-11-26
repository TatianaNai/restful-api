package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.todoapp.arguments_providers.TodoArgumentsProvider;
import ru.todoapp.data.entities.Todo;
import ru.todoapp.data.repositories.TodoRepository;
import ru.todoapp.rest.constants.StatusCodes;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;
import ru.todoapp.rest.services.TodoApiServiceImpl;
import ru.todoapp.services.TodoService;

import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

@SpringBootTest
public class AddTodoTest extends BaseTest {

    @Autowired
    private TodoApiServiceImpl todoApiService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        TodoRequest expectedTodo = new TodoRequest(
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());

        assertSuccessfulResponse(todoService.createTodo(expectedTodo), StatusCodes.CREATED);

        Todo todoFromDB = todoRepository.getByText(expectedTodo.getText()).get(0);
        List<TodoResponse> todos = todoApiService.getListTodo();

        assertIfExistTodoInDBAndAPIResponse(todos, todoFromDB, expectedTodo);

        todoService.deleteTodo(expectedTodo);
    }

    @ParameterizedTest
    @ArgumentsSource(TodoArgumentsProvider.class)
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(TodoRequest todo) {
        assertUnsuccessfulResponse(todoService.createTodo(todo), StatusCodes.BAD_REQUEST);
        todoRepository.findByTextIsNullOrCompletedIsNull()
                .forEach(todoRepository::delete);
    }
}
