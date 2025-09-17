package ru.todoApp.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.atomic.AtomicLong;

public class RandomGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final AtomicLong COUNTER = new AtomicLong();

    public static boolean getRandomBoolean() {
        return RandomUtils.nextBoolean();
    }

    public static String getRandomStringByLength(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RandomUtils.nextInt(0, CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static Long getRandomLongId() {
        return COUNTER.getAndIncrement();
    }
}
