package com.measure.measure_water_gas.adapters.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus status;
    private String error_description;
    private String error_code;
    private String stack;

    public RestErrorMessage(HttpStatus status, String message, String errorCode, Throwable throwable) {
        this.status = status;
        this.error_description = message;
        this.error_code = errorCode;
        this.stack = throwable != null ? getStackTrace(throwable) : null;
    }

    public int getStatus() {
        return status.value();
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }
        return stackTrace.toString();
    }


}
