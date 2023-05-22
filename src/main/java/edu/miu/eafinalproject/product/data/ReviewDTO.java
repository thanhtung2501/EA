package edu.miu.eafinalproject.product.data;

import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.product.domain.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private String title;
    private String description;
    private int stars;
    private LocalDate date;

    private Customer customer;

    private Product product;
}
