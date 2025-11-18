package ru.todoapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.todoapp.models.Todo;

import java.util.ArrayList;
import java.util.List;

import static ru.todoapp.utils.RandomGenerator.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoApiServiceImpl todoApiService;
    private final TodoIdService todoIdService;

    public List<Long> createTodosWithAmount(int amountTodos) {
        log.info("Add {} todos", amountTodos);
        List<Long> todoIds = new ArrayList<>();
        for (int i = 0; i < amountTodos; i++) {
            long id = todoIdService.generateId();
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
