package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.interfaces.repository.CustomerRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.GetCustomerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCustomerUseCaseImpl implements GetCustomerUseCase {
    private final CustomerRepository customerRepository;

    public GetCustomerUseCaseImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute (UUID customerCode) {
        return customerRepository.findById(customerCode).orElse(null);
    }
}
