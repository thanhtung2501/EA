package edu.miu.eafinalproject.data;

import edu.miu.eafinalproject.domain.Customer;
import edu.miu.eafinalproject.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String title;
    private String description;
    private int stars;
    private LocalDate date;

    private Customer2DTO customer;

    private ProductDTO product;
}
