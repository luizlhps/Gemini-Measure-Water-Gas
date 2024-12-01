package com.measure.measure_water_gas.application.dtos;

import com.measure.measure_water_gas.application.enums.MeasureTypeEnum;
import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.shared.annotations.DtoReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MeasureCreateInputDto {
    private Instant measureDatetime;
    private String measureType;
    private boolean hasConfirmed;
    private String imageUrl;
    private CustomerDTO customer;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CustomerDTO {
        public UUID customerCode;
    }
}

