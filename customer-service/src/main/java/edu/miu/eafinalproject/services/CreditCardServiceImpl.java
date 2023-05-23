package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.CreditCardDTO;
import edu.miu.eafinalproject.domain.CreditCard;
import edu.miu.eafinalproject.repositories.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements CreditCardService{
    @Autowired
    CreditCardRepository cardRepository;
    @Autowired
    CreditCardAdaptor cardAdaptor;
    @Override
    public CreditCardDTO getCreditCard(Long creditCardId) {
        CreditCard card = cardRepository.findById(creditCardId).orElseThrow();
        return cardAdaptor.convertCreditCardToCreditCardDto(card);
    }

    @Override
    public CreditCardDTO updateCreditCard(CreditCardDTO creditCardDTO) {
        CreditCard saved = cardRepository.save(cardAdaptor.convertCreditCardDtoToCreditCard(creditCardDTO));
        return cardAdaptor.convertCreditCardToCreditCardDto(saved);
    }

    @Override
    public void deleteCreditCard(Long creditCardId) {
        Optional<CreditCard> card = cardRepository.findById(creditCardId);
        if(card.isPresent()){
            cardRepository.deleteById(creditCardId);
        }
    }

    @Override
    public List<CreditCardDTO> getAllCreditCards() {
        List<CreditCard> cards = cardRepository.findAll();
        return cards.stream().map(creditCard -> cardAdaptor.convertCreditCardToCreditCardDto(creditCard)).collect(Collectors.toList());
    }

    @Override
    public CreditCardDTO addCreditCard(CreditCardDTO creditCardDTO) {
        CreditCard card = cardRepository.save(cardAdaptor.convertCreditCardDtoToCreditCard(creditCardDTO));
        return cardAdaptor.convertCreditCardToCreditCardDto(card);
    }
}
