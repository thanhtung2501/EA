package edu.miu.eafinalproject.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private String title;
    private String description;
    private int stars;
    private LocalDate date;

    private long customerId;

    private ProductDTO product;
}
