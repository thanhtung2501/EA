package edu.miu.eafinalproject.shoppingcart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productNumber;

    private int quantity;
    private double discountValue;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
}
