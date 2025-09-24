package ru.todoapp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class PostPerformanceTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Check performance")
    public void shouldHaveCorrectAddTodoPerformance() {
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;

        for (int i = 0; i < randomIntWithBorders(5, 20); i++) {
            long todoId = generateId();
            TodoModel todo = new TodoModel(todoId,
                    randomStringWithLength(randomIntWithBorders(5, 100)),
                    randomBoolean());
            long responseTime = todoRestService.post(todo, HttpStatus.SC_CREATED)
                    .extract()
                    .time();
            log.info("POST /todos executed in {} ms", responseTime);
            minTime = Math.min(minTime, responseTime);
            maxTime = Math.max(maxTime, responseTime);
            removeId(todoId);
        }
        log.info("Min time: {} ms, Max time: {} ms", minTime, maxTime);
    }
}
