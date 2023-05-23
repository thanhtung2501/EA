package edu.miu.eafinalproject.product.data;

import lombok.Data;

@Data
public class AddressDTO {
    private long id;

    private AddressType addressType;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
