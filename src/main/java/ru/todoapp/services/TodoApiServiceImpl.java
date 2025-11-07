package ru.todoapp.services;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.todoapp.constants.TodoHeaders;
import ru.todoapp.interceptors.HeaderInterceptor;
import ru.todoapp.models.Todo;

import java.util.List;
import java.util.Objects;

import static ru.todoapp.utils.Props.getProperty;

public class TodoApiServiceImpl {

    @SneakyThrows
    public Response<List<Todo>> get() {
        return getTodoApiService().listTodos().execute();
    }

    @SneakyThrows
    public Response<List<Todo>> getWithLimit(int limit) {
        return getTodoApiService().listTodosWithLimit(limit).execute();
    }

    @SneakyThrows
    public Response<List<Todo>> getWithOffset(int offset) {
        return getTodoApiService().listTodosWithOffset(offset).execute();
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
        return getTodoApiService().createTodo(todo).execute();
    }

    @SneakyThrows
    public Response<Void> put(Long id, Todo todo) {
        return getTodoApiService().updateTodo(id, todo).execute();
    }

    @SneakyThrows
    public Response<Void> delete(Long id) {
        return getTodoApiService().deleteTodo(id, TodoHeaders.AUTHORIZATION).execute();
    }

    private TodoApiService getTodoApiService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getProperty("baseUri")
                        .replace("${port}", getProperty("port")))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TodoApiService.class);
    }
}
