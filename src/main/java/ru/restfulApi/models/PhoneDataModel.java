package ru.restfulApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneDataModel {
    private Double price;
    private String color;
    @JsonProperty("capacity GB")
    private Integer capacityGB;
}
