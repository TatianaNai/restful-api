package ru.todoApp;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.todoApp.models.TodoModel;
import ru.todoApp.services.TodoRestService;
import ru.todoApp.utils.Props;
import ru.todoApp.utils.RandomGenerator;

@Slf4j
public class PostPerformanceTest extends BaseTest {
    private final TodoRestService todoRestService = new TodoRestService();

    @Test
    @DisplayName("Check performance")
    public void shouldHaveCorrectAddTodoPerformance() {
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;

        for(int i = 0; i < Props.getIntProperty("repetitionsForPostPerformance"); i++) {
            TodoModel todo = new TodoModel(RandomGenerator.getRandomLongId(),
                    RandomGenerator.getRandomStringByLength(Props.getIntProperty("textLength")),
                    RandomGenerator.getRandomBoolean());
            long responseTime = todoRestService.post(todo, HttpStatus.SC_CREATED)
                    .extract()
                    .time();
            log.info("POST /todos executed in {} ms", responseTime);
            minTime = Math.min(minTime, responseTime);
            maxTime = Math.max(maxTime, responseTime);
        }
        log.info("Min time: {} ms, Max time: {} ms", minTime, maxTime);
    }
}
