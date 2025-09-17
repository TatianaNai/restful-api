package ru.restfulApi.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CapacityGB {
    GB16(16),
    GB32(32),
    GB64(64),
    GB128(128),
    GB256(256),
    GB512(512);

    private final int value;

    CapacityGB(int value) {
        this.value = value;
    }
}
