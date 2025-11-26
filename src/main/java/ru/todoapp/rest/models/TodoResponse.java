package ru.todoapp.rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoResponse {
    private Long id;
    private String text;
    private Boolean completed;
}
