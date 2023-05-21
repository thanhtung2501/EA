package edu.miu.eafinalproject.product.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.miu.eafinalproject.config.ByteArrayDeserializer;
import edu.miu.eafinalproject.config.ByteArraySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonSerialize(using = ByteArraySerializer.class)
    @JsonDeserialize(using = ByteArrayDeserializer.class)
    private byte[] image;

    private String barcodeNumber;
    private int quantityInStock;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}
