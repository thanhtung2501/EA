package edu.miu.eafinalproject.product.services;

import edu.miu.eafinalproject.product.domain.Customer;

public interface CustomerService {
    Customer createOrCustomer(Customer customer);
    void deleteCustomer(Long customerId);

}
