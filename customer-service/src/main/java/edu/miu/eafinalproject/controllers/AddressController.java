package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.data.AddressDTO;
import edu.miu.eafinalproject.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable("addressId") long addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }
}
