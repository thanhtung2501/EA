package edu.miu.eafinalproject.shoppingcart.domain;

import edu.miu.eafinalproject.product.data.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
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

//    @ManyToOne
    @Transient
    private Customer customer;

    @Transient
    private List<CartItem> cartItems = new ArrayList<>();

}
