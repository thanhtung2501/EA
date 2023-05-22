package edu.miu.eafinalproject.product.repositories;

import edu.miu.eafinalproject.product.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
