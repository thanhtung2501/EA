package edu.miu.eafinalproject.shoppingcart.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findFirstByCustomerId(long customerId);
}
