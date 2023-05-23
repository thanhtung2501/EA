package edu.miu.eafinalproject.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    private long id;
    private String number;
    private LocalDate expirationDate;
    private String securityCode;
}
