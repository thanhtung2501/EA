package edu.miu.eafinalproject.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private Long shoppingCartNumber;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL, CascadeType.PERSIST}, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

}
