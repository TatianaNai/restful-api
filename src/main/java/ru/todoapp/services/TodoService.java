package ru.todoapp.services;

import lombok.extern.slf4j.Slf4j;
import ru.todoapp.models.Todo;

import java.util.ArrayList;
import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

@Slf4j
public class TodoService {
    public List<Long> createTodosWithAmount(int amountTodos) {
        log.info("Add {} todos", amountTodos);
        TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
        List<Long> todoIds = new ArrayList<>();
        for (int i = 0; i < amountTodos; i++) {
            long id = TodoIdService.INSTANCE.generateId();
            Todo todo = new Todo(id,
                    randomStringWithLength(randomIntWithBorders(5, 100)),
                    randomBoolean());
            todoIds.add(id);
            log.info("Add todo with id {}", id);
            todoApiService.post(todo);
        }
        return todoIds;
    }
}
