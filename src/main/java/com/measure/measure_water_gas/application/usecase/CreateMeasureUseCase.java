package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.application.dtos.NewMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureOutputDto;
import com.measure.measure_water_gas.domain.entity.Measure;
import com.measure.measure_water_gas.domain.interfaces.repository.MeasureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class CreateMeasureUseCase {
    private MeasureRepository measureRepository;
    private ModelMapper modelMapper;

    @Autowired
    CreateMeasureUseCase(
            MeasureRepository measureRepository
            , ModelMapper modelMapper) {
        this.measureRepository = measureRepository;
        this.modelMapper = modelMapper;
    }


    public NewMeasureOutputDto execute(NewMeasureInputDto input) {
        Measure measure = modelMapper.map(input, Measure.class);

        // verificar se existe uma measure naquele mês
        Instant dateMeasure = input.getMeasureDatetime();
        List<Measure> existsByMonthAndYear = measureRepository.findByMeasureDatetime(dateMeasure);

        measure.setHasConfirmed(true);

        //measureRepository.save(measure);

        return null;
    }
}
