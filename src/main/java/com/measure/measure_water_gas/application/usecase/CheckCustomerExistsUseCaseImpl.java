package com.measure.measure_water_gas.application.usecase;

import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.interfaces.repository.CustomerRepository;
import com.measure.measure_water_gas.domain.interfaces.usecase.CheckCustomerExistsUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CheckCustomerExistsUseCaseImpl implements CheckCustomerExistsUseCase {
    private final CustomerRepository customerRepository;

    public CheckCustomerExistsUseCaseImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Boolean execute (UUID customerCode) {
        return customerRepository.existsById(customerCode);
    }
}
