package edu.miu.eafinalproject.product.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<Review> reviews;
    private AddressResponse billingAddressResponse;
    private List<AddressResponse> shippingAddressResponses;
    private List<CreditCard> creditCards;
}
