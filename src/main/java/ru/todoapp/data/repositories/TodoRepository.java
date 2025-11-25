package ru.todoapp.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.todoapp.data.entities.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> getByText(String text);
}
