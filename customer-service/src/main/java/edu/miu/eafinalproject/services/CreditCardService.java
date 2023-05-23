package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.CreditCardDTO;
import edu.miu.eafinalproject.domain.CreditCard;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CreditCardService {
    CreditCardDTO getCreditCard(Long creditCardId);
    CreditCardDTO updateCreditCard(CreditCard creditCard);
    void deleteCreditCard(Long creditCardId);
    List<CreditCardDTO> getAllCreditCards();

    CreditCardDTO addCreditCard(CreditCard creditCard);
}
