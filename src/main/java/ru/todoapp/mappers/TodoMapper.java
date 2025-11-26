package ru.todoapp.mappers;

import ru.todoapp.data.entities.Todo;
import ru.todoapp.rest.models.TodoRequest;
import ru.todoapp.rest.models.TodoResponse;

public class TodoMapper {

    public static TodoResponse entityToResponse(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getText(),
                entity.getCompleted()
        );
    }

    public static Todo todoRequestToEntity(TodoRequest todo) {
        return new Todo(
                todo.getText(),
                todo.getCompleted()
        );
    }
}
