package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.application.dtos.CustomerCreateOutputDto;
import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.interfaces.repository.CustomerRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.CreateCustomerUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    public CreateCustomerUseCaseImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CustomerCreateOutputDto execute() {

        Customer customer = new Customer();
        Customer customerCreated = customerRepository.save(customer);

        return modelMapper.map(customerCreated, CustomerCreateOutputDto.class);
    }
}
