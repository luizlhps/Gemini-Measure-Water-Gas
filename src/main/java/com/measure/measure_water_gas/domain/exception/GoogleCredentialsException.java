package com.measure.measure_water_gas.domain.exception;

import com.measure.measure_water_gas.shared.exception.CustomException;
import org.springframework.http.HttpStatus;

public class GoogleCredentialsException extends CustomException {
    private final HttpStatus status;
    private final String code;

    public GoogleCredentialsException(){
        super("Arquivo de credenciais não encontrado no classpath.\nPor favor, adicione o arquivo 'credentials.google.json' com suas credenciais da conta de serviço.");
        this.status = HttpStatus.valueOf(500);
        this.code = "CREDENTIALS_NOT_CONFIGURED";
    }
}
