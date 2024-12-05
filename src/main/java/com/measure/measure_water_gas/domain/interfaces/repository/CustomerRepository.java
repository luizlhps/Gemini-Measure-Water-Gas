package com.measure.measure_water_gas.domain.interfaces.repository;

import com.measure.measure_water_gas.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE c.customerCode = :customerCode AND c.isDeleted = false")
    Optional<Customer> findActiveByCustomerCode(@Param("customerCode") UUID customerCode);
}