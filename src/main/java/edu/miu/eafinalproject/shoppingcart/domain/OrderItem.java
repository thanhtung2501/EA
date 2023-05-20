package edu.miu.eafinalproject.shoppingcart.domain;

import edu.miu.eafinalproject.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private double discountValue;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
