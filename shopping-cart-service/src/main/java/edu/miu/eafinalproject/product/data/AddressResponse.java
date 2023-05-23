package edu.miu.eafinalproject.product.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {
    private long id;
    private AddressType addressType;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
