package edu.miu.eafinalproject.product.controllers;

import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.addProduct(product));
    }
}
