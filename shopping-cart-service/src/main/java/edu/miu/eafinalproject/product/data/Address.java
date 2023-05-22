package edu.miu.eafinalproject.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;
    private AddressType addressType;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
