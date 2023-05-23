package edu.miu.eafinalproject.shoppingcart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long shoppingCartNumber;

    private double totalPrice;

    private long customerId;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

}
