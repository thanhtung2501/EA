package edu.miu.eafinalproject.data;

import edu.miu.eafinalproject.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private Long productNumber;
    private String name;
    private String description;
    private Double price;
    private String barcodeNumber;
    private int quantityInStock;
    private List<Review> reviews;
    private byte[] image;
}
