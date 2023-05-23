package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.domain.Customer;
import edu.miu.eafinalproject.services.CustomerService;
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

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> createCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerByCustomerId(customerId));
    }
}
