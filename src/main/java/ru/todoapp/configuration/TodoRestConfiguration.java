package ru.todoapp.configuration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.todoapp.interceptors.HeaderInterceptor;
import ru.todoapp.services.TodoApiService;

@Configuration
public class TodoRestConfiguration {

    @Bean
    public HeaderInterceptor headerInterceptor() {
        return new HeaderInterceptor();
    }

    @Bean
    public OkHttpClient okHttpClient(HeaderInterceptor headerInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Bean
    public TodoApiService todoApiService(OkHttpClient okHttpClient, @Value("${api.baseUri}") String baseUri) {
        return new Retrofit.Builder()
                .baseUrl(baseUri)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TodoApiService.class);
    }
}
