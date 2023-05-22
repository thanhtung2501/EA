package edu.miu.eafinalproject.product.repositories;

import edu.miu.eafinalproject.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductNumber(Long productNumber);
}
