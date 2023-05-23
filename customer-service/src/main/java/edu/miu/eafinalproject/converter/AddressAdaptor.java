package edu.miu.eafinalproject.converter;

import edu.miu.eafinalproject.data.AddressDTO;
import edu.miu.eafinalproject.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressAdaptor {
    public edu.miu.eafinalproject.data.AddressDTO convertAddressToAddressDto(Address address){
       if (address == null) return AddressDTO.builder().build();

       return edu.miu.eafinalproject.data.AddressDTO.builder()
               .addressType(address.getAddressType())
               .id(address.getId())
               .street(address.getStreet())
               .state(address.getState())
               .city(address.getCity())
               .country(address.getCountry())
               .postalCode(address.getPostalCode())
               .build();
    }

    public Address convertAddressDTOToAddress(AddressDTO addressDTO){
       if (addressDTO == null) return Address.builder().build();

       return Address.builder()
               .addressType(addressDTO.getAddressType())
               .street(addressDTO.getStreet())
               .state(addressDTO.getState())
               .city(addressDTO.getCity())
               .country(addressDTO.getCountry())
               .postalCode(addressDTO.getPostalCode())
               .build();
    }
}
