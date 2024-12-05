package com.measure.measure_water_gas.adapters.web.controller;

import com.measure.measure_water_gas.application.dtos.ConfirmMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.ConfirmMeasureOutputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureOutputDto;
import com.measure.measure_water_gas.domain.interfaces.usecase.ConfirmMeasureUseCase;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateMeasureUseCase;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/measures")
public class MeasureController {

    private final CreateMeasureUseCase createMeasureUseCase;
    private final ConfirmMeasureUseCase confirmMeasureUseCase;


    @Autowired
    public MeasureController(CreateMeasureUseCase createMeasureUseCase, ConfirmMeasureUseCase confirmMeasureUseCase) {
        this.createMeasureUseCase = createMeasureUseCase;
        this.confirmMeasureUseCase = confirmMeasureUseCase;
    }


    @PostMapping()
    public ResponseEntity<NewMeasureOutputDto> create(@RequestBody NewMeasureInputDto newMeasureInputDto) throws ConflictException, NotFoundException {
        return ResponseEntity.ok(createMeasureUseCase.execute(newMeasureInputDto));
    }

    @PatchMapping("/{measure_uuid}/confirm")
    public ResponseEntity<ConfirmMeasureOutputDto> confirm(
            @PathVariable("measure_uuid") UUID measureUuid,
            @RequestBody ConfirmMeasureInputDto confirmMeasureInputDto)
            throws ConflictException, NotFoundException {

        confirmMeasureInputDto.setMeasureUuid(measureUuid);
        return ResponseEntity.ok(confirmMeasureUseCase.execute(confirmMeasureInputDto));
    }
}
