package ru.todoapp;

import ru.todoapp.services.TodoIdService;

public abstract class BaseTest {
    public long generateId() {
        return TodoIdService.INSTANCE.generateId();
    }

    public void removeId(long id) {
        TodoIdService.INSTANCE.removeId(id);
    }
}
