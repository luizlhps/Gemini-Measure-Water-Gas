package com.measure.measure_water_gas.adapters.web.controller;

import com.measure.measure_water_gas.domain.entity.Customer;
import com.measure.measure_water_gas.domain.interfaces.usecase.GetCustomerUseCase;
import com.measure.measure_water_gas.shared.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final GetCustomerUseCase getCustomerUseCase;


    @Autowired
    public CustomerController(GetCustomerUseCase getCustomerUseCase) {
        this.getCustomerUseCase = getCustomerUseCase;
    }


    @GetMapping("/{customer_code}")
    public ResponseEntity<Customer> create(@PathVariable("customer_code") UUID customer_code ) throws NotFoundException {
        Customer customer = getCustomerUseCase.execute(customer_code);

        if(customer == null) {
            throw new NotFoundException("Usuario não encontrado", "NOT_FOUND");
        }

        return ResponseEntity.ok(customer);
    }
}
