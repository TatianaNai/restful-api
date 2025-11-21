package ru.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.todoapp.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
