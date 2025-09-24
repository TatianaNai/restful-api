package ru.todoapp.services;

import retrofit2.Call;
import retrofit2.http.*;
import ru.todoapp.constants.TodoApiEndpoints;
import ru.todoapp.models.Todo;

import java.util.List;

public interface TodoApiService {
    @GET(TodoApiEndpoints.TODOS)
    Call<List<Todo>> listTodos();

    @GET(TodoApiEndpoints.TODOS)
    Call<List<Todo>> listTodosWithLimit(@Query("limit") int limit);

    @GET(TodoApiEndpoints.TODOS)
    Call<List<Todo>> listTodosWithOffset(@Query("offset") int offset);

    @POST(TodoApiEndpoints.TODOS)
    Call<Void> createTodo(@Body Todo todo);

    @PUT(TodoApiEndpoints.TODOS_WITH_ID)
    Call<Void> updateTodo(@Path("id") long id, @Body Todo todo);

    @DELETE(TodoApiEndpoints.TODOS_WITH_ID)
    Call<Void> deleteTodo(@Path("id") long id, @Header("Authorization") String auth);
}
