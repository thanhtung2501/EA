package edu.miu.eafinalproject.shoppingcart.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartProduct {
    private int quantity;
    private Long shoppingCartNumber;
    private Long productNumber;
}
