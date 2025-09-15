package ru.restfulApi.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceDataModel {
    private String price;
    private String color;
}
