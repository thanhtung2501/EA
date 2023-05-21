package edu.miu.eafinalproject.product.services;

import edu.miu.eafinalproject.product.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    Product addProduct(Product product);
}
