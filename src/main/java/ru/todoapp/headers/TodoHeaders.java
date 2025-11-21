package ru.todoapp.headers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TodoHeaders {
    private final String login;
    private final String password;

    public TodoHeaders(@Value("${api.login}") String login,
                       @Value("${api.password}") String password) {
        this.login = login;
        this.password = password;
    }

    public String getAuthHeader() {
        return "Basic " + Base64.getEncoder()
                .encodeToString((login + ":" + password).getBytes());
    }
}
