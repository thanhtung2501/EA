package edu.miu.eafinalproject.product.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private Long id;
    private Long productNumber;
    private String name;
    private String description;
    private Double price;
    private String barcodeNumber;
    private int quantityInStock;
    private List<Review> reviews;
}
