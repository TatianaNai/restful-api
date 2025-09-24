package ru.todoapp;

import ru.todoapp.services.TodoIdService;

import java.util.List;

public abstract class BaseTest {
    public long generateId() {
        return TodoIdService.INSTANCE.generateId();
    }

    public void removeId(long id) {
        TodoIdService.INSTANCE.removeId(id);
    }

    public void removeIdsByList(List<Long> ids) {
        ids.forEach(this::removeId);
    }
}
