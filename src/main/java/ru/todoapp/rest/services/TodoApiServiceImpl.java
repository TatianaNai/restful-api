package ru.todoapp.rest.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import ru.todoapp.rest.headers.TodoHeaders;
import ru.todoapp.rest.models.TodoResponse;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoApiServiceImpl {
private final TodoApiService todoApiService;
private final TodoHeaders todoHeaders;

    @SneakyThrows
    public Response<List<TodoResponse>> get() {
        return todoApiService.listTodos().execute();
    }

    @SneakyThrows
    public Response<List<TodoResponse>> getWithLimit(int limit) {
        return todoApiService.listTodosWithLimit(limit).execute();
    }

    @SneakyThrows
    public Response<List<TodoResponse>> getWithOffset(int offset) {
        return todoApiService.listTodosWithOffset(offset).execute();
    }

    @SneakyThrows
    public List<TodoResponse> getListTodo() {
        return Objects.requireNonNull(get().body()).stream().toList();
    }

    @SneakyThrows
    public Response<Void> post(TodoResponse todo) {
        return todoApiService.createTodo(todo).execute();
    }

    @SneakyThrows
    public Response<Void> put(Long id, TodoResponse todo) {
        return todoApiService.updateTodo(id, todo).execute();
    }

    @SneakyThrows
    public Response<Void> delete(Long id) {
        return todoApiService.deleteTodo(id, todoHeaders.getAuthHeader()).execute();
    }
}
