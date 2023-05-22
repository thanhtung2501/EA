package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ProductDTO;
import edu.miu.eafinalproject.domain.Product;
import edu.miu.eafinalproject.repositories.ProductRepository;
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
    public ProductDTO getProduct(Long productNumber) {
        Optional<Product> optionalProduct = productRepository.findByProductNumber(productNumber);
        Product product = optionalProduct.orElse(new Product());

        return getProductDTO(product);
    }

    private ProductDTO getProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .barcodeNumber(product.getBarcodeNumber())
                .reviews(product.getReviews())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .productNumber(product.getProductNumber())
                .build();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}
