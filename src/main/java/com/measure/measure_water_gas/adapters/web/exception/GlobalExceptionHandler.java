package com.measure.measure_water_gas.adapters.web.exception;

import com.measure.measure_water_gas.adapters.web.exception.RestErrorMessage;
import com.measure.measure_water_gas.shared.exception.CustomException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestErrorMessage> handleCustomException(CustomException ex) {
        RestErrorMessage errorResponse = new RestErrorMessage(ex.getStatus(), ex.getMessage(), ex.getCode(), ex);
        return new ResponseEntity<RestErrorMessage>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGeneralException(Exception ex) {
        RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno.", "INTERNAL_SERVER_ERROR", ex);
        return new ResponseEntity<RestErrorMessage>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}