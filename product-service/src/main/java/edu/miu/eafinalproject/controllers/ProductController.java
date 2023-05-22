package edu.miu.eafinalproject.controllers;

import edu.miu.eafinalproject.domain.Product;
import edu.miu.eafinalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{productNumber}")
    public ResponseEntity<?> getProduct(@PathVariable("productNumber") Long productNumber){
        try {
            return ResponseEntity.ok(productService.getProduct(productNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam("image") MultipartFile imageFile,
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

        try {
            return ResponseEntity.ok(productService.addProduct(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
