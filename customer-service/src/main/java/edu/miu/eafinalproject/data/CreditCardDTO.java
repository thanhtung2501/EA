package edu.miu.eafinalproject.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreditCardDTO {
    private String number;
    private LocalDate expirationDate;
    private String securityCode;
}
