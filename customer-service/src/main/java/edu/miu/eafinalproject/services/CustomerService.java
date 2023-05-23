package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.CustomerDTO;
import edu.miu.eafinalproject.domain.Customer;

import java.util.List;

public interface CustomerService {
    CustomerDTO createOrCustomer(Customer customer);
    CustomerDTO getCustomerByCustomerId(Long customerId);
    void deleteCustomer(Long customerId);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO updateCustomer(CustomerDTO customerDTO);
}
