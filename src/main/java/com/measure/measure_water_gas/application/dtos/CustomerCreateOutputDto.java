package com.measure.measure_water_gas.application.dtos;

import com.measure.measure_water_gas.domain.entity.Measure;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateOutputDto {
    private UUID customerCode;

    private boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

    private List<Measure> measures;
}
