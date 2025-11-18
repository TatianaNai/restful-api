package ru.todoapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.todoapp.arguments_providers.TodoArgumentsProvider;
import ru.todoapp.constants.StatusCodes;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoApiServiceImpl;

import static ru.todoapp.utils.RandomGenerator.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddTodoTest extends BaseTest {
    @Autowired
    private TodoApiServiceImpl todoApiService;

    @RepeatedTest(3)
    @DisplayName("Add todo")
    public void shouldHaveCorrectAddTodo() {
        long todoId = generateId();
        Todo todo = new Todo(todoId,
                randomStringWithLength(randomIntWithBorders(5, 100)),
                randomBoolean());

        assertSuccessfulResponse(todoApiService.post(todo), StatusCodes.CREATED);

        removeId(todoId);
    }

    @ParameterizedTest
    @ArgumentsSource(TodoArgumentsProvider.class)
    @DisplayName("Add todo with missing parameter. Negative test")
    public void shouldNotAllowAddTodoWithMissingParameter(Todo todo) {
        assertUnsuccessfulResponse(todoApiService.post(todo), StatusCodes.BAD_REQUEST);
    }
}
