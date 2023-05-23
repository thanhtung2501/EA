package edu.miu.eafinalproject.shoppingcart.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@Entity
public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productNumber;

    private int quantity;
    private double discountValue;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
    private Orders orders;
}
