package edu.miu.eafinalproject.product.services;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address createOrUpdateAddress(Address address) {
        return addressRepository.save(address);
    }
}
