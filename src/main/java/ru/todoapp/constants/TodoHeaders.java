package ru.todoapp.constants;

import java.util.Base64;

import static ru.todoapp.utils.Props.getProperty;

public class TodoHeaders {
    public static final String AUTHORIZATION = "Basic " + Base64.getEncoder()
            .encodeToString((getProperty("login") + ":" + getProperty("password")).getBytes());
}
