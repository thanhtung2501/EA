package edu.miu.eafinalproject.data;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Customer2DTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;

    private AddressDTO billingAddress;

}