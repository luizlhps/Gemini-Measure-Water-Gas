package com.measure.measure_water_gas.domain.interfaces.usecase;

import com.measure.measure_water_gas.domain.entity.Customer;

import java.util.UUID;

public interface GetCustomerUseCase {
    Customer execute (UUID customerCode);
}
