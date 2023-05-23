package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ProductDTO;
import edu.miu.eafinalproject.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    ProductDTO getProduct(Long id);
    Product addProduct(Product product);
}
