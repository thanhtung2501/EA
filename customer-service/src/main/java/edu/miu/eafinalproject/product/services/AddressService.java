package edu.miu.eafinalproject.product.services;

import edu.miu.eafinalproject.product.domain.Address;

public interface AddressService {
    Address createOrUpdateAddress(Address address);
}
