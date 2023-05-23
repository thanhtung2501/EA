package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ProductDTO;
import edu.miu.eafinalproject.domain.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProduct(Long id);
    ProductDTO addProduct(ProductDTO product);
    ProductDTO updateProduct(ProductDTO updateProduct);

    void deleteProduct(Long id);


}
