package com.measure.measure_water_gas.domain.interfaces.repository;

import com.measure.measure_water_gas.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}