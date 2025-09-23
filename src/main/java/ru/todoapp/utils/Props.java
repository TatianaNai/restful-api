package ru.todoapp.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Props {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(Props.class.getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String getProperty(String key) {
        String property = System.getProperty(key);
        return property == null ? PROPERTIES.getProperty(key) : property;
    }
}
