package com.measure.measure_water_gas.domain.interfaces.usecase;

import com.measure.measure_water_gas.application.dtos.NewMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureOutputDto;
import com.measure.measure_water_gas.domain.entity.Measure;
import com.measure.measure_water_gas.domain.interfaces.repository.MeasureRepository;
import com.measure.measure_water_gas.shared.exception.BadRequestException;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface CreateMeasureUseCase {
    NewMeasureOutputDto execute(NewMeasureInputDto input) throws ConflictException, NotFoundException;
}
