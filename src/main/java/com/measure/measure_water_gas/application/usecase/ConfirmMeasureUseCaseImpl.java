
package com.measure.measure_water_gas.application.usecase;


import com.measure.measure_water_gas.application.dtos.ConfirmMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.ConfirmMeasureOutputDto;
import com.measure.measure_water_gas.domain.entity.Measure;
import com.measure.measure_water_gas.domain.interfaces.repository.CustomerRepository;
import com.measure.measure_water_gas.domain.interfaces.repository.MeasureRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.ConfirmMeasureUseCase;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfirmMeasureUseCaseImpl implements ConfirmMeasureUseCase {
    private MeasureRepository measureRepository;
    private ModelMapper modelMapper;

    public ConfirmMeasureUseCaseImpl(MeasureRepository measureRepository, ModelMapper modelMapper) {
        this.measureRepository = measureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ConfirmMeasureOutputDto execute(ConfirmMeasureInputDto input) throws ConflictException, NotFoundException {
        Measure measure = measureRepository.findById(input.getMeasureUuid())
                .orElseThrow(() -> new NotFoundException("Medição não encontrada", "NOT_FOUND"));


        if(measure.isHasConfirmed()) {
            throw new ConflictException("Esta leitura já foi feita!", "MEASURE_ALREADY_CONFIRMED");
        }

        measure.setHasConfirmed(true);
        measureRepository.save(measure);

        return new ConfirmMeasureOutputDto(true);
    }
}
