package com.measure.measure_water_gas.domain.interfaces.usecase;

import java.util.Optional;
import java.util.UUID;

public interface CheckCustomerExistsUseCase {
    Boolean execute (UUID customerCode);
}
