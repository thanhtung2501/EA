package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.data.CreditCardDTO;
import edu.miu.eafinalproject.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @GetMapping
    public ResponseEntity<List<CreditCardDTO>> getAllCreditCards(){
        return ResponseEntity.ok(creditCardService.getAllCreditCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCard(@PathVariable Long id){
        return ResponseEntity.ok(creditCardService.getCreditCard(id));
    }

    @PostMapping
    public ResponseEntity<CreditCardDTO> addCreditCard(@RequestBody CreditCardDTO creditCardDTO){
        return ResponseEntity.ok(creditCardService.addCreditCard(creditCardDTO));
    }
    @DeleteMapping("/{id}")
    public void deleteCreditCard(@PathVariable Long id){
        creditCardService.deleteCreditCard(id);
    }

    @PutMapping
    public ResponseEntity<CreditCardDTO> updateCreditCard(@RequestBody CreditCardDTO creditCardDTO){
        return ResponseEntity.ok(creditCardService.updateCreditCard(creditCardDTO));
    }

}
