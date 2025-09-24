package ru.todoapp.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import ru.todoapp.models.TodoModel;

import java.util.ArrayList;
import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class TodoService {
    public List<Long> createTodosWithAmount(int amountTodos) {
        log.info("Add {} todos", amountTodos);
        TodoRestService todoRestService = new TodoRestService();
        List<Long> todoIds = new ArrayList<>();
        for (int i = 0; i < amountTodos; i++) {
            long id = TodoIdService.INSTANCE.generateId();
            TodoModel todo = new TodoModel(id,
                    randomStringWithLength(randomIntWithBorders(5, 100)),
                    randomBoolean());
            todoIds.add(id);
            log.info("Add todo with id {}", id);
            todoRestService.post(todo, HttpStatus.SC_CREATED);
        }
        return todoIds;
    }
}
