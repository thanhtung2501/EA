package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.AddressDTO;

public interface AddressService {
    AddressDTO createOrUpdateAddress(AddressDTO addressDTO);
    AddressDTO getAddressById(long addressId);
}
