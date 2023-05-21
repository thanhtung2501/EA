package edu.miu.eafinalproject.product.services;

import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(new Product());
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
