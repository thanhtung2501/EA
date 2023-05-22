package edu.miu.eafinalproject.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditCardDTO {
    private String number;
    private LocalDate expirationDate;
    private String securityCode;
}
