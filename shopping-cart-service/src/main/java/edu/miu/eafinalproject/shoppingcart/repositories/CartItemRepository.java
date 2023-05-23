package edu.miu.eafinalproject.shoppingcart.repositories;

import edu.miu.eafinalproject.shoppingcart.domain.CartItem;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select c from CartItem c join fetch ShoppingCart s where s.id = :cartId")
    Optional<List<CartItem>> findAllByCartId(@Param("cartId") long shoppingCartId);
}
