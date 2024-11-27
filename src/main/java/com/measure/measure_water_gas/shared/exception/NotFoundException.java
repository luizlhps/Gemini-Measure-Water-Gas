package com.measure.measure_water_gas.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends CustomException {
    private final HttpStatus status;
    private final String code;

    public NotFoundException(String message, String code) {
        super(message);
        this.status = HttpStatus.valueOf(404);
        this.code = code;
    }
}
