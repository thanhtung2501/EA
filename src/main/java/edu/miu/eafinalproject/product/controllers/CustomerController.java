package edu.miu.eafinalproject.product.controllers;

import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.product.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createOrCustomer(customer));
    }
}
