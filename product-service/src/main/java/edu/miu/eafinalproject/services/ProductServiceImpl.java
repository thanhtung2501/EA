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

    @Autowired
    private  ConverterAdaptor converterAdaptor;

    @Override
    public List<ProductDTO> getAllProducts() {
        return converterAdaptor.convertProductListToProductDTOList(productRepository.findAll());
    }

    @Override
    public ProductDTO getProduct(Long productNumber) {
        Optional<Product> optionalProduct = productRepository.findByProductNumber(productNumber);
        Product product = optionalProduct.orElse(new Product());
        return converterAdaptor.convertProductToProductDTO(product);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = converterAdaptor.convertProductDTOtoProduct(productDTO);
        return converterAdaptor.convertProductToProductDTO(productRepository.save(product)) ;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO updateProductDTO) {
        Optional<Product> optionalProduct = productRepository.findByProductNumber(updateProductDTO.getProductNumber());
        Product existingProduct = optionalProduct.orElseThrow(()-> new IllegalArgumentException("Product not found"));
        existingProduct.setName(updateProductDTO.getName());
        existingProduct.setDescription(updateProductDTO.getDescription());
        existingProduct.setPrice(updateProductDTO.getPrice());
        existingProduct.setBarcodeNumber(updateProductDTO.getBarcodeNumber());
        existingProduct.setQuantityInStock(updateProductDTO.getQuantityInStock());
        existingProduct.setImage(updateProductDTO.getImage());
        return converterAdaptor.convertProductToProductDTO(productRepository.save(existingProduct));

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
