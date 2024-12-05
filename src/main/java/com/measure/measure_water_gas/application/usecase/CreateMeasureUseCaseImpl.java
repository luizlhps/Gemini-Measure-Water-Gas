package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.application.dtos.*;
import com.measure.measure_water_gas.application.factories.StorageFactory;
import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.entity.Measure;
import com.measure.measure_water_gas.domain.enums.StorageProviderTypeEnum;
import com.measure.measure_water_gas.domain.interfaces.repository.MeasureRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateCustomerUseCase;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateMeasureUseCase;
import com.measure.measure_water_gas.domain.interfaces.usecase.GetCustomerUseCase;
import com.measure.measure_water_gas.shared.exception.ConflictException;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CreateMeasureUseCaseImpl implements CreateMeasureUseCase {
    private MeasureRepository measureRepository;
    private ModelMapper modelMapper;
    private CreateCustomerUseCase createCustomerUseCase;
    private GetCustomerUseCase getCustomerUseCase;
    private VertexAiGeminiChatModel chatModel;
    private StorageFactory storageFactory;

    @Autowired
    CreateMeasureUseCaseImpl(
            MeasureRepository measureRepository
            , ModelMapper modelMapper
            , GetCustomerUseCase getCustomerUseCase
            , CreateCustomerUseCase createCustomerUseCase, VertexAiGeminiChatModel chatModel, StorageFactory storageFactory
    ) {
        this.measureRepository = measureRepository;
        this.modelMapper = modelMapper;
        this.getCustomerUseCase = getCustomerUseCase;
        this.createCustomerUseCase = createCustomerUseCase;
        this.chatModel = chatModel;
        this.storageFactory = storageFactory;
    }

    public NewMeasureOutputDto execute(NewMeasureInputDto input) throws ConflictException, NotFoundException {

        // verificar se existe uma measure naquele mês
        validateMeasureForMonth(input);

        //checar se o customer existe caso não crie um
        Customer customer = getCustomer(input);

        // validação do arquivo
        String base64File = input.getImage();
        byte[] fileData = Base64.decodeBase64(base64File);

        Tika tika = new Tika();
        String mimeTypeStr = tika.detect(fileData);

        MimeType mimeType = MimeType.valueOf(mimeTypeStr);

        // enviar os dados para GEMINI
        double valor = processImageAndExtractValue(fileData, mimeType);

        // uploud da imagem para o storage
        var url = storageFactory
                .getProvider(StorageProviderTypeEnum.GOOGLE)
                .uploadFile(UUID.randomUUID().toString(), fileData, mimeType.toString());

        // criar o measure
        MeasureCreateOutputDto measureCreateOutputDto = createMeasure(input, url, customer);

        return new NewMeasureOutputDto(url, valor, measureCreateOutputDto.getUuid());
    }

    private void validateMeasureForMonth(NewMeasureInputDto input) throws ConflictException {
        Instant dateMeasure = input.getMeasure_datetime().truncatedTo(ChronoUnit.DAYS);
        List<Measure> existsByMonthAndYear = measureRepository.findByMeasureDatetime(dateMeasure);

        if (!existsByMonthAndYear.isEmpty()) {
            throw new ConflictException("Leitura do mês já realizada", "DOUBLE_REPORT");
        }
    }

    private Customer getCustomer(NewMeasureInputDto input) throws ConflictException, NotFoundException {
        NewMeasureInputDto.CustomerDTO customerDto = input.getCustomer();

        if (customerDto != null) {
            UUID customerCode = customerDto.getCustomerCode();
            Customer customer = getCustomerUseCase.execute(customerCode);

            if (customer == null) {
                throw new NotFoundException("Nenhuma leitura encontrada.", "CUSTOMER_NOT_FOUND");
            }

            //setar o customer
            return customer;

        } else {
            // criar o customer
            CustomerCreateOutputDto customerCreateOutputDto = createCustomerUseCase.execute();
            Customer customer = modelMapper.map(customerCreateOutputDto, Customer.class);
            return customer;
        }

    }

    private Double processImageAndExtractValue(byte[] fileData, MimeType mimeType) {
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

        return valor;
    }

    private MeasureCreateOutputDto createMeasure (NewMeasureInputDto input, String url, Customer customer) {
        MeasureCreateInputDto measureCreateInputDto = new MeasureCreateInputDto(
                input.getMeasure_datetime(),
                input.getMeasure_type().toString(),
                false,
                url,
                new MeasureCreateInputDto.CustomerDTO(customer.getCustomerCode())
        );

        Measure measure = modelMapper.map(measureCreateInputDto, Measure.class);
        Measure measureCreated = measureRepository.save(measure);

        return modelMapper.map(measureCreated, MeasureCreateOutputDto.class);
    }
}
