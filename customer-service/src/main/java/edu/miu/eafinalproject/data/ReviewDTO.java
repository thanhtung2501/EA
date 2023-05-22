package edu.miu.eafinalproject.data;

import edu.miu.eafinalproject.domain.Customer;
import edu.miu.eafinalproject.domain.Product;
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
