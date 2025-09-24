package ru.todoapp.services;

import ru.todoapp.models.Todo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum TodoIdService {
    INSTANCE;
    private static final List<Long> testIds = new CopyOnWriteArrayList<>();

    public synchronized long generateId() {
        TodoApiServiceImpl todoApiService = new TodoApiServiceImpl();
        List<Long> todosIds = todoApiService.get().body().stream()
                .map(Todo::getId).toList();
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

        if (todoApiService.get().body().stream().map(Todo::getId).anyMatch(toDoId -> toDoId == id)) {
            todoApiService.delete(id);
        }
    }
}
