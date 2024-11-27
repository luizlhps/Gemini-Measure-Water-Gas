package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.application.dtos.CustomerCreateOutputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureInputDto;
import com.measure.measure_water_gas.application.dtos.NewMeasureOutputDto;
import com.measure.measure_water_gas.domain.entity.Measure;
import com.measure.measure_water_gas.domain.interfaces.repository.MeasureRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.CheckCustomerExistsUseCase;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateCustomerUseCase;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateMeasureUseCase;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.vertexai.gemini.MimeTypeDetector;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CreateMeasureUseCaseImpl implements CreateMeasureUseCase {
    private MeasureRepository measureRepository;
    private ModelMapper modelMapper;
    private CreateCustomerUseCase createCustomerUseCase;
    private CheckCustomerExistsUseCase checkCustomerExistsUseCase;
    private VertexAiGeminiChatModel chatModel;

    @Autowired
    CreateMeasureUseCaseImpl(
            MeasureRepository measureRepository
            , ModelMapper modelMapper
            , CheckCustomerExistsUseCase checkCustomerExistsUseCase
            , CreateCustomerUseCase createCustomerUseCase, VertexAiGeminiChatModel chatModel
    ) {
        this.measureRepository = measureRepository;
        this.modelMapper = modelMapper;
        this.checkCustomerExistsUseCase = checkCustomerExistsUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.chatModel = chatModel;
    }

    public NewMeasureOutputDto execute(NewMeasureInputDto input) throws ConflictException, NotFoundException {
        Measure measure = modelMapper.map(input, Measure.class);

        // verificar se existe uma measure naquele mês
        Instant dateMeasure = input.getMeasure_datetime();
        List<Measure> existsByMonthAndYear = measureRepository.findByMeasureDatetime(dateMeasure);

        if (existsByMonthAndYear.size() > 0) {
            throw new ConflictException("Leitura do mês já realizada", "DOUBLE_REPORT");
        }

        //checar se o customer existe caso não crie um
        NewMeasureInputDto.CustomerDTO customerDto = input.getCustomer();

        if (customerDto != null) {
            UUID customerCode = customerDto.getCustomerCode();
            boolean existsCustomer = checkCustomerExistsUseCase.execute(customerCode);

            if (!existsCustomer) {
                throw new NotFoundException("Nenhuma leitura encontrada.", "CUSTOMER_NOT_FOUND");
            }
        } else {
            // criar o customer
            CustomerCreateOutputDto customerCreateOutputDto = createCustomerUseCase.execute();
        }


        // enviar os dados para GEMINI
        String base64File = input.getImage();
        byte[] fileData = Base64.decodeBase64(base64File);

        Tika tika = new Tika();
        String mimeTypeStr = tika.detect(fileData);

        MimeType mimeType = MimeType.valueOf(mimeTypeStr);

        ByteArrayResource byteArrayResource = new ByteArrayResource(fileData);


        var userMessage = new UserMessage("""
                1 - ME FALE O VALOR DA CONTA ENVIADA
                2 - A SAÍDA DEVE SER APENAS NUMEROS.
                3 - EVITE ESPAÇOS, VIRGULA, USE PONTO.
                4 - EXEMPLO DE SAÍDA: 120.00
                5 - EXEMPLO DE SÁIDA ERRADA: 120,00 ( OU COM ESPAÇOS/LETRAS)
                6 - EXEMPLO DE SAÍDA CASO NÃO ENCONTRE O VALOR: 0
                """,
                List.of(new Media(mimeType, byteArrayResource)));

        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage)));

        var content = response.getResult().getOutput().getContent();

        double valor = Double.parseDouble(content.trim());

        NewMeasureOutputDto newMeasureOutputDto = new NewMeasureOutputDto("alo/src", valor, UUID.randomUUID());

        return newMeasureOutputDto;
    }
}
