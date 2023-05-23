package edu.miu.eafinalproject.converter;

import edu.miu.eafinalproject.data.CustomerDTO;
import edu.miu.eafinalproject.domain.Address;
import edu.miu.eafinalproject.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerAdapter {
    
    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        if (customer == null) return CustomerDTO.builder().build();

        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .emailAddress(customer.getEmailAddress())
                .billingAddress(convertAddressToAddressDTO(customer.getBillingAddress()))
                .build();
    }

    public Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .id(customerDTO.getId())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .emailAddress(customerDTO.getEmailAddress())
                .billingAddress(convertAddressDTOToAddress(customerDTO.getBillingAddress()))
                .build();
    }
    
    public edu.miu.eafinalproject.data.AddressDTO convertAddressToAddressDTO(Address address){
        return edu.miu.eafinalproject.data.AddressDTO.builder()
                .addressType(address.getAddressType())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

    public Address convertAddressDTOToAddress(edu.miu.eafinalproject.data.AddressDTO addressDTO){
        return Address.builder()
                .addressType(addressDTO.getAddressType())
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .postalCode(addressDTO.getPostalCode())
                .country(addressDTO.getCountry())
                .build();
    }
}
