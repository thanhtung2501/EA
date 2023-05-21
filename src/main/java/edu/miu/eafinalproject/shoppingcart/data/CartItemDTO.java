package edu.miu.eafinalproject.shoppingcart.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartItemDTO {
    private Long id;
    private ProductDTO item;
    private int quantity;
}
