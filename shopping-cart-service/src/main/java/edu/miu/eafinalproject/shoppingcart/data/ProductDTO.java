package edu.miu.eafinalproject.shoppingcart.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private long productNumber;
    private String productName;
    private double price;
}
