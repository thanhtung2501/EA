package edu.miu.eafinalproject.data;

import edu.miu.eafinalproject.domain.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private Long productNumber;
    private String name;
    private String description;
    private Double price;
    private String barcodeNumber;
    private int quantityInStock;
    private List<Review> reviews;
}
