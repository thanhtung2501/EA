package edu.miu.eafinalproject.shoppingcart.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
