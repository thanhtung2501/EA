package edu.miu.eafinalproject.product.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String title;
    private String description;
    private int stars;
    private LocalDate date;
    private Customer customer;
    private ProductResponse productResponse;
}
