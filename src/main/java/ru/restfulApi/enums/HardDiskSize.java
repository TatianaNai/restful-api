package ru.restfulApi.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum HardDiskSize {
    TB1("1 TB"),
    TB2("2 TB"),
    TB4("4 TB"),
    TB8("8 TB");

    private final String value;

    HardDiskSize(String value) {
        this.value = value;
    }
}
