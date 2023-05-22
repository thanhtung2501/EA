package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.domain.Address;
import edu.miu.eafinalproject.repositories.AddressRepository;
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
