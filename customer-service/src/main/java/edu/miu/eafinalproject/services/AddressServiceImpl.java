package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.converter.AddressAdaptor;
import edu.miu.eafinalproject.data.AddressDTO;
import edu.miu.eafinalproject.domain.Address;
import edu.miu.eafinalproject.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressAdaptor addressAdaptor;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public AddressDTO createOrUpdateAddress(AddressDTO addressDTO) {
        Address address = addressAdaptor.convertAddressDTOToAddress(addressDTO);
        addressRepository.save(address);
        return addressAdaptor.convertAddressToAddressDto(address);
    }

    @Override
    public edu.miu.eafinalproject.data.AddressDTO getAddressById(long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        return addressAdaptor.convertAddressToAddressDto(optionalAddress.orElse(null));
    }
}
