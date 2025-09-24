package ru.todoapp.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum TodoIdService {
    INSTANCE;
    private static final List<Long> testIds = new CopyOnWriteArrayList<>();

    public synchronized long generateId() {
        TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
        List<Long> todosIds = todoApiService.getListId();
        long randomValue = (long) (Math.random() * ((100_000_000) + 1));

        if (todosIds.contains(randomValue) || testIds.contains(randomValue)) {
            return generateId();
        }
        testIds.add(randomValue);
        return randomValue;
    }

    public synchronized void removeId(long id) {
        TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
        testIds.remove(id);

        if (todoApiService.getListId().stream().anyMatch(toDoId -> toDoId == id)) {
            todoApiService.delete(id);
        }
    }
}
