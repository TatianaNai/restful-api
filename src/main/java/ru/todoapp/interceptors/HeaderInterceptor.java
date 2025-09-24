package ru.todoapp.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request modifiedRequest = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(modifiedRequest);
    }
}
