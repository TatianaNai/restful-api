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

import static ru.todoapp.utils.Props.getProperty;

public class TodoApiServiceImpl {
    public TodoApiService getTodoService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getProperty("baseUri")
                        .replace("${port}", getProperty("port")))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TodoApiService.class);
    }

    @SneakyThrows
    public Response<List<Todo>> get() {
        return getTodoService().listTodos().execute();
    }

    @SneakyThrows
    public Response<Void> post(Todo todo) {
        return getTodoService().createTodo(todo).execute();
    }

    @SneakyThrows
    public Response<Void> delete(Long id) {
        return getTodoService().deleteTodo(id, TodoHeaders.AUTHORIZATION).execute();
    }
}
