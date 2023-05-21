package edu.miu.eafinalproject.shoppingcart.data.request;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.shoppingcart.domain.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private Customer customer;
    private Address shippingAddress;
    private List<CartItem> cartItems;
}
