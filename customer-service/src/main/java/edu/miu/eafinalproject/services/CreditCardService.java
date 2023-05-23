package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.CreditCardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CreditCardService {
    CreditCardDTO getCreditCard(Long creditCardId);
    CreditCardDTO updateCreditCard(CreditCardDTO creditCardDTO);
    void deleteCreditCard(Long creditCardId);
    List<CreditCardDTO> getAllCreditCards();

    CreditCardDTO addCreditCard(CreditCardDTO creditCardDTO);
}
