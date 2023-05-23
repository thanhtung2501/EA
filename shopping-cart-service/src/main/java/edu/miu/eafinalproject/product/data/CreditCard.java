package edu.miu.eafinalproject.product.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(unique = true)
    private String number;
    private LocalDate expirationDate;
    private String securityCode;
}
