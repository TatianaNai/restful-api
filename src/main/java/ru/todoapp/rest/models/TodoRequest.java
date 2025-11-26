package ru.todoapp.rest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TodoRequest {
    private String text;
    private Boolean completed;
}
