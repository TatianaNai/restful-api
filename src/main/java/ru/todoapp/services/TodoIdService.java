package ru.todoapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class TodoIdService {
    private final TodoApiServiceImpl todoApiService;
    private static final List<Long> TEST_IDS = new CopyOnWriteArrayList<>();

    public synchronized long generateId() {
        List<Long> todosIds = todoApiService.getListId();
        long randomValue = (long) (Math.random() * ((100_000_000) + 1));

        if (todosIds.contains(randomValue) || TEST_IDS.contains(randomValue)) {
            return generateId();
        }
        TEST_IDS.add(randomValue);
        return randomValue;
    }

    public synchronized void removeId(long id) {
        TEST_IDS.remove(id);

        if (todoApiService.getListId().stream().anyMatch(toDoId -> toDoId == id)) {
            todoApiService.delete(id);
        }
    }
}
