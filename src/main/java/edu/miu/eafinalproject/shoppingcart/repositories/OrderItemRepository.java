package edu.miu.eafinalproject.shoppingcart.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
