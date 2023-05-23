package edu.miu.eafinalproject.data;

import edu.miu.eafinalproject.domain.AddressType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private Long id;

    private AddressType addressType;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
