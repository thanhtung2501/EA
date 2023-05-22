package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.domain.Address;

public interface AddressService {
    Address createOrUpdateAddress(Address address);
}
