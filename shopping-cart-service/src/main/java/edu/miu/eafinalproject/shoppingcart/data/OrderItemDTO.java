package edu.miu.eafinalproject.shoppingcart.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private double discountValue;
}
