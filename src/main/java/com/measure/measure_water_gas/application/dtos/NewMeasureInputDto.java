package com.measure.measure_water_gas.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.measure.measure_water_gas.application.enums.MeasureTypeEnum;
import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.shared.annotations.DtoReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Reference;

import java.time.Instant;
import java.util.Date;

@Data
public class NewMeasureInputDto {
    @Schema(description = "URL da imagem associada à medição", example = "http://example.com/image.jpg")
    private Instant measureDatetime;

    @NotNull
    @Schema(description = "Tipo da medição", example = "WATER")
    private MeasureTypeEnum measureType;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "Data e hora da medição", example = "2024-11-24T10:30:00")
    private String imageUrl;;

    @NotNull
    @Schema(description = "Informações do cliente associado à medição")
    @DtoReference(Customer.class)
    private CustomerDTO customer;

    @Data
    private static class CustomerDTO {
        private String customerCode;
    }
}

