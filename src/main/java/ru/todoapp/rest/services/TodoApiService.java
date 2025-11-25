package ru.todoapp.rest.services;

import retrofit2.Call;
import retrofit2.http.*;
import ru.todoapp.rest.constants.TodoApiEndpoints;
import ru.todoapp.rest.models.TodoResponse;

import java.util.List;

public interface TodoApiService {
    @GET(TodoApiEndpoints.TODOS)
    Call<List<TodoResponse>> listTodos();

    @GET(TodoApiEndpoints.TODOS)
    Call<List<TodoResponse>> listTodosWithLimit(@Query("limit") int limit);

    @GET(TodoApiEndpoints.TODOS)
    Call<List<TodoResponse>> listTodosWithOffset(@Query("offset") int offset);

    @POST(TodoApiEndpoints.TODOS)
    Call<Void> createTodo(@Body TodoResponse todo);

    @PUT(TodoApiEndpoints.TODOS_WITH_ID)
    Call<Void> updateTodo(@Path("id") long id, @Body TodoResponse todo);

    @DELETE(TodoApiEndpoints.TODOS_WITH_ID)
    Call<Void> deleteTodo(@Path("id") long id, @Header("Authorization") String auth);
}
