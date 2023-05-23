package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.CreditCardDTO;
import edu.miu.eafinalproject.domain.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class CreditCardAdaptor {
    public CreditCard convertCreditCardDtoToCreditCard(CreditCardDTO creditCardDTO){
        return CreditCard.builder()
                .id(creditCardDTO.getId())
                .number(creditCardDTO.getNumber())
                .expirationDate(creditCardDTO.getExpirationDate())
                .securityCode(creditCardDTO.getSecurityCode())
                .build();
    }

    public CreditCardDTO convertCreditCardToCreditCardDto(CreditCard creditCard){
       return CreditCardDTO.builder()
               .id(creditCard.getId())
               .number(creditCard.getNumber())
               .expirationDate(creditCard.getExpirationDate())
               .securityCode(creditCard.getSecurityCode())
               .build();
    }
}
