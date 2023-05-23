package edu.miu.eafinalproject.shoppingcart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    private LocalDate orderDate;
    private double totalPrice;

    private long customerId;

    private long shippingAddressId;

    @Transient
    private List<OrderItem> orderItems = new ArrayList<>();

}
