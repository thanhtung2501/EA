package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.converter.CustomerAdapter;
import edu.miu.eafinalproject.data.CustomerDTO;
import edu.miu.eafinalproject.domain.Customer;
import edu.miu.eafinalproject.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAdapter customerAdapter;

    @Override
    public CustomerDTO createOrCustomer(Customer customer) {
        Customer customerResponse =  customerRepository.save(customer);
        return customerAdapter.convertCustomerToCustomerDTO(customerResponse);
    }

    @Override
    public CustomerDTO getCustomerByCustomerId(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = optionalCustomer.orElse(null);
        return customerAdapter.convertCustomerToCustomerDTO(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
       return customerList.stream().map(customer -> customerAdapter.convertCustomerToCustomerDTO(customer)).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).orElse(null);
        if(customer == null){
            return null;
        }
        Customer customer1= customerRepository.save(customerAdapter.convertCustomerDTOToCustomer(customerDTO));
        return customerAdapter.convertCustomerToCustomerDTO(customer1);
    }

}
