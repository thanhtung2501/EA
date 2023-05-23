package edu.miu.eafinalproject.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReviewDTO {
    private String title;
    private String description;
    private int stars;
    private LocalDate date;

    private long customerId;

    private ProductDTO product;
}
