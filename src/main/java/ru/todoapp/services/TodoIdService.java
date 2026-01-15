package ru.todoapp.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum TodoIdService {
    INSTANCE;
    private static final List<Long> testIds = new CopyOnWriteArrayList<>();

    public synchronized long generateId() {
        TodoRestService todoRestService = new TodoRestService();
        List<Long> todosIds = todoRestService.getListId();
        long randomValue = (long) (Math.random() * ((100_000_000) + 1));

        if (todosIds.contains(randomValue) || testIds.contains(randomValue)) {
            return generateId();
        }
        testIds.add(randomValue);
        return randomValue;
    }

    public synchronized void removeId(long id) {
        TodoRestService todoRestService = new TodoRestService();
        testIds.remove(id);

        if (todoRestService.getListId().stream().anyMatch(toDoId -> toDoId == id)) {
            todoRestService.deleteResponse(id);
        }
    }
}
