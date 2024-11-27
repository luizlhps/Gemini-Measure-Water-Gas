package com.measure.measure_water_gas.domain.interfaces.repository;

import com.measure.measure_water_gas.domain.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {
    List<Measure> findByMeasureDatetime(Instant measureDatetime);
}
