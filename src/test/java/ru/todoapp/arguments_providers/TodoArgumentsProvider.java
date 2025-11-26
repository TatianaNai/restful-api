package ru.todoapp.arguments_providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.todoapp.rest.models.TodoRequest;

import java.util.stream.Stream;

import static ru.todoapp.utils.RandomGenerator.randomBoolean;
import static ru.todoapp.utils.RandomGenerator.randomIntWithBorders;
import static ru.todoapp.utils.RandomGenerator.randomStringWithLength;

public class TodoArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(TodoRequest.builder()
                        .completed(randomBoolean())
                        .build()),
                Arguments.of(TodoRequest.builder()
                        .text(randomStringWithLength(randomIntWithBorders(5, 100)))
                        .build())
        );
    }
}
