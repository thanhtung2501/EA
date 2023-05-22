package edu.miu.eafinalproject.product.data;

import edu.miu.eafinalproject.product.domain.AddressType;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    private AddressType addressType;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
