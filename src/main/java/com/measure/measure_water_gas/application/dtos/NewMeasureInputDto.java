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
import java.util.Optional;
import java.util.UUID;

@Data
public class NewMeasureInputDto {
    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "Data e hora da medição", example = "2024-11-24T10:30:00")
    private String image;

    @Schema(description = "Informações do cliente associado à medição")
    @DtoReference(Customer.class)
    private CustomerDTO customer;

    @Schema(description = "URL da imagem associada à medição", example = "http://example.com/image.jpg")
    private Instant measure_datetime;

    @NotNull
    @Schema(description = "Tipo da medição", example = "WATER")
    private MeasureTypeEnum measure_type;



    @Data
    public static class CustomerDTO {
        @NotNull(message = "Customer code is required when customer is provided.")
        @Size(min = 1, max = 50)
        public UUID customerCode;
    }
}

