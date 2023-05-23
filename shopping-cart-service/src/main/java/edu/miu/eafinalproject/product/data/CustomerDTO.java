package edu.miu.eafinalproject.product.data;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private AddressResponse billingAddress;

}
