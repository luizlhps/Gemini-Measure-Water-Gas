package com.measure.measure_water_gas.domain.interfaces.usecase;

import com.measure.measure_water_gas.application.dtos.ConfirmMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.ConfirmMeasureOutputDto;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;

public interface ConfirmMeasureUseCase {
    ConfirmMeasureOutputDto execute(ConfirmMeasureInputDto input) throws ConflictException, NotFoundException;
}
