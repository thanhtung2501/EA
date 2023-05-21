package edu.miu.eafinalproject.shoppingcart.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByShoppingCartNumber(Long shoppingCartNumber);
}
