package ru.restfulApi.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DeviceColor {
    WHITE("White"),
    BLACK("Black"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow"),
    RED("Red"),
    PURPLE("Purple"),
    BROWN("Brown");

    private final String value;

    DeviceColor(String value) {
        this.value = value;
    }
}
