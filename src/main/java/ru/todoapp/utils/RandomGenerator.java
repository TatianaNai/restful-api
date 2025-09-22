package ru.todoapp.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import ru.todoapp.models.TodoModel;
import ru.todoapp.services.TodoRestService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static ru.todoapp.utils.Props.getIntProperty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final List<Long> testIds = new CopyOnWriteArrayList<>();

    public static boolean randomBoolean() {
        return RandomUtils.nextBoolean();
    }

    public static String randomStringWithLength(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RandomUtils.nextInt(0, CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static int randomIntWithBorders(int minNumber, int maxNumber) {
        return ThreadLocalRandom.current().nextInt(minNumber, maxNumber);
    }

    public static synchronized long generateId() {
        TodoRestService todoRestService = new TodoRestService();
        List<Long> todosIds = todoRestService.getListId();
        long randomValue = (long) (Math.random() * ((100_000_000) + 1));

        if (todosIds.contains(randomValue) || testIds.contains(randomValue)) {
            return generateId();
        }
        testIds.add(randomValue);
        return randomValue;
    }

    public static synchronized void removeId(long id) {
        TodoRestService todoRestService = new TodoRestService();
        testIds.remove(id);

        if (todoRestService.getListId().stream().anyMatch(toDoId -> toDoId == id)) {
            todoRestService.deleteById(id, HttpStatus.SC_NO_CONTENT);
        }
    }

    public static List<Long> generateTodosWithAmount(int amountTodos) {
        TodoRestService todoRestService = new TodoRestService();
        List<Long> todoIds = new ArrayList<>();
        for (int i = 0; i < amountTodos; i++) {
            long id = generateId();
            TodoModel todo = new TodoModel(id,
                    randomStringWithLength(getIntProperty("textLength")),
                    randomBoolean());
            todoIds.add(id);
            todoRestService.post(todo, HttpStatus.SC_CREATED);
        }
        return todoIds;
    }
}
