package com.measure.measure_water_gas.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ConfirmMeasureInputDto {
    @NotNull(message = "O codigo de medição precisa ser inserido!.")
    @Size(min = 1, max = 50)
    private UUID measureUuid;
}
