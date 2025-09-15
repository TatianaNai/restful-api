package ru.restfulApi.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceModel<T> {
    private String id;
    private String name;
    private T data;
}
