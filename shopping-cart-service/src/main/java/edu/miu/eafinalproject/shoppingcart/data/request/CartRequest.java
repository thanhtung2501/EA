package edu.miu.eafinalproject.shoppingcart.data.request;

import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private long customerId;
    private long shoppingCartNumber;
    private long shippingAddressId;
    private OrderState orderState;
    private List<CartItemRequest> cartItems;

    @Data
    public static class CartItemRequest {
        private long productNumber;
        private int quantity;
        private double discountValue;
    }
}
