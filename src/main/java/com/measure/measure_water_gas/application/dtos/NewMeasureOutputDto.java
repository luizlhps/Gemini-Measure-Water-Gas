package com.measure.measure_water_gas.application.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class NewMeasureOutputDto {
    private String imageUrl;
    private double measureValue;
    private UUID measureUuid;
}
