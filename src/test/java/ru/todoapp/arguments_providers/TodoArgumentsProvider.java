package ru.todoapp.arguments_providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.todoapp.models.Todo;
import ru.todoapp.services.TodoIdService;

import java.util.stream.Stream;

import static ru.todoapp.utils.RandomGenerator.randomBoolean;
import static ru.todoapp.utils.RandomGenerator.randomIntWithBorders;
import static ru.todoapp.utils.RandomGenerator.randomStringWithLength;

public class TodoArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        TodoIdService todoIdService = SpringExtension.getApplicationContext(context)
                .getBean(TodoIdService.class);
        return Stream.of(
                Arguments.of(Todo.builder()
                        .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(Todo.builder()
                        .id(todoIdService.generateId())
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(Todo.builder()
                        .id(todoIdService.generateId())
                        .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                        .build())
        );
    }
}
