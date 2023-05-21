package edu.miu.eafinalproject.shoppingcart.domain;

import edu.miu.eafinalproject.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartLine {
    private Product product;
    private int quantity;
}
