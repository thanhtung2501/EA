package edu.miu.eafinalproject.shoppingcart.domain;

import edu.miu.eafinalproject.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    private Product product;
    private ShoppingCart cart;
    private int quantity;
    private double discountValue;
}
