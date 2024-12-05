package com.measure.measure_water_gas.domain.interfaces.repository;

import com.measure.measure_water_gas.domain.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface MeasureRepository extends JpaRepository<Measure, UUID> {
    @Query("SELECT m FROM Measure m WHERE FUNCTION('DATE_TRUNC', 'DAY', m.measureDatetime) = FUNCTION('DATE_TRUNC', 'DAY', CAST(:measureDatetime AS TIMESTAMP))")
    List<Measure> findByMeasureDatetime(@Param("measureDatetime") Instant measureDatetime);
}
