package edu.miu.eafinalproject.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<Review> reviews;
    private Address billingAddress;
    private List<Address> shippingAddresses;
    private List<CreditCard> creditCards;
}
