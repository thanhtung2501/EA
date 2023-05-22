package edu.miu.eafinalproject.shoppingcart.data;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;

    private ProductDTO product;

    private int quantity;
    private double discountValue;
}
