package edu.miu.eafinalproject.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productNumber;

    private String name;
    private String description;
    private Double price;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Blob image;

    private String barcodeNumber;
    private int quantityInStock;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}
