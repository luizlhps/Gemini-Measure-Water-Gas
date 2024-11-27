package com.measure.measure_water_gas.adapters.web.controller;

import com.measure.measure_water_gas.application.dtos.NewMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureOutputDto;
import com.measure.measure_water_gas.application.usecase.CreateMeasureUseCase;
import com.measure.measure_water_gas.shared.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measures")
public class MeasureController {

    private final CreateMeasureUseCase createMeasureUseCase;


    @Autowired
    public MeasureController(CreateMeasureUseCase createMeasureUseCase) {
        this.createMeasureUseCase = createMeasureUseCase;
    }


    @PostMapping("/new")
    public ResponseEntity<NewMeasureOutputDto> create(@RequestBody NewMeasureInputDto newMeasureInputDto) throws CustomException {
      return ResponseEntity.ok(createMeasureUseCase.execute(newMeasureInputDto));
    }
}
