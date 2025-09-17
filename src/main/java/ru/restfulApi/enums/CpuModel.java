package ru.restfulApi.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CpuModel {
    INTEL("Intel Core i9"),
    APPLE_PRO("Apple M1 Pro"),
    APPLE_MAX("Apple M2 Max");

    private final String value;

    CpuModel(String value) {
        this.value = value;
    }
}
