package edu.miu.eafinalproject.shoppingcart.data.request;

import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private Long customerId;
    private Long shoppingCartNumber;
    private Long shippingAddressId;
    private OrderState orderState;
    private List<CartItemRequest> cartItems;

    @Data
    public static class CartItemRequest {
        private Long productNumber;
        private Integer quantity;
        private double discountValue;
    }
}
