package edu.miu.eafinalproject.services;

import edu.miu.eafinalproject.data.ProductDTO;
import edu.miu.eafinalproject.data.ReviewDTO;
import edu.miu.eafinalproject.domain.Product;
import edu.miu.eafinalproject.domain.Review;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverterAdaptor {
    public ProductDTO convertProductToProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .barcodeNumber(product.getBarcodeNumber())
                .quantityInStock(product.getQuantityInStock())
                .reviews(product.getReviews())
                .image(product.getImage())
                .build();

    }
    public Product convertProductDTOtoProduct(ProductDTO productDTO){
        return Product.builder()
                .id(productDTO.getId())
                .productNumber(productDTO.getProductNumber())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .barcodeNumber(productDTO.getBarcodeNumber())
                .quantityInStock(productDTO.getQuantityInStock())
                .reviews(productDTO.getReviews())
                .image(productDTO.getImage())
                .build();
    }
    public List<ProductDTO> convertProductListToProductDTOList(List<Product> productList){
        return productList.stream().map(this::convertProductToProductDTO).toList();
    }


}
