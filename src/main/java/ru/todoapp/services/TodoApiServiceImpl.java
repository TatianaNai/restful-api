package ru.todoapp.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import ru.todoapp.headers.TodoHeaders;
import ru.todoapp.models.Todo;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoApiServiceImpl {
private final TodoApiService todoApiService;
private final TodoHeaders todoHeaders;

    @SneakyThrows
    public Response<List<Todo>> get() {
        return todoApiService.listTodos().execute();
    }

    @SneakyThrows
    public Response<List<Todo>> getWithLimit(int limit) {
        return todoApiService.listTodosWithLimit(limit).execute();
    }

    @SneakyThrows
    public Response<List<Todo>> getWithOffset(int offset) {
        return todoApiService.listTodosWithOffset(offset).execute();
    }

    @SneakyThrows
    public List<Long> getListId() {
        return Objects.requireNonNull(get().body()).stream().map(Todo::getId).toList();
    }

    @SneakyThrows
    public List<Todo> getListTodo() {
        return Objects.requireNonNull(get().body()).stream().toList();
    }

    @SneakyThrows
    public Response<Void> post(Todo todo) {
        return todoApiService.createTodo(todo).execute();
    }

    @SneakyThrows
    public Response<Void> put(Long id, Todo todo) {
        return todoApiService.updateTodo(id, todo).execute();
    }

    @SneakyThrows
    public Response<Void> delete(Long id) {
        return todoApiService.deleteTodo(id, todoHeaders.getAuthHeader()).execute();
    }
}
