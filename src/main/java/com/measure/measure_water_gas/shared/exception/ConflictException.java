package com.measure.measure_water_gas.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends CustomException {
    private final HttpStatus status;
    private final String code;

    public ConflictException(String message, String code) {
        super(message);
        this.status = HttpStatus.valueOf(409);
        this.code = code;
    }
}
