package edu.miu.eafinalproject.product.controllers;

import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productNumber}")
    public ResponseEntity<Product> getProduct(@PathVariable("productNumber") Long productNumber){
        return ResponseEntity.ok(productService.getProduct(productNumber));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestParam("image") MultipartFile imageFile,
                                              @RequestParam("productNumber") Long productNumber,
                                              @RequestParam("name") String name,
                                              @RequestParam("description") String description,
                                              @RequestParam("price") double price,
                                              @RequestParam("barcodeNumber") String barcodeNumber,
                                              @RequestParam("quantityInStock") int quantityInStock) {
        byte[] imageBytes;
        try {
            imageBytes = imageFile.getBytes();
        } catch (IOException e) {
            // Handle the exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Product product = new Product();
        product.setProductNumber(productNumber);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setBarcodeNumber(barcodeNumber);
        product.setQuantityInStock(quantityInStock);
        product.setImage(imageBytes);

        return ResponseEntity.ok(productService.addProduct(product));
    }
}
