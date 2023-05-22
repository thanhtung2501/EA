package edu.miu.eafinalproject.product.repositories;

import edu.miu.eafinalproject.product.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
