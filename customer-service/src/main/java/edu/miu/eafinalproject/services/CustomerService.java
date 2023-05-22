package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.domain.Customer;

public interface CustomerService {
    Customer createOrCustomer(Customer customer);
    void deleteCustomer(Long customerId);

}
