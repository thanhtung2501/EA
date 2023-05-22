package edu.miu.eafinalproject.shoppingcart.data;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShoppingCartDTO {
    private Long shoppingCartNumber;
    private List<CartItemDTO> cartItems;
}
