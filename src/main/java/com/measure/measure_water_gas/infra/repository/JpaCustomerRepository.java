package com.measure.measure_water_gas.infra.repository;

import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.interfaces.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, UUID>, CustomerRepository {
}