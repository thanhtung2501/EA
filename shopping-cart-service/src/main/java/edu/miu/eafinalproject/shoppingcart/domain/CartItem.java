package edu.miu.eafinalproject.shoppingcart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long productNumber;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    private int quantity;

    private double discountValue;
}
