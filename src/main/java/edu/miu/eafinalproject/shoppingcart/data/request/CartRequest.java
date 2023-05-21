package edu.miu.eafinalproject.shoppingcart.data.request;

import edu.miu.eafinalproject.product.domain.Address;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private Long customerId;
    private Address shippingAddress;
    private List<CartItemRequest> cartItems;

    @Data
    public static class CartItemRequest {
        private Long productNumber;
        private Integer quantity;
        private double discountValue;
    }
}
