package edu.miu.eafinalproject.shoppingcart.controllers;

import edu.miu.eafinalproject.shoppingcart.data.OrderDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import edu.miu.eafinalproject.shoppingcart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/{shoppingCartNumber}")
    public ResponseEntity<?> getShoppingCart(@PathVariable("shoppingCartNumber") Long shoppingCartNumber){
        try {
            return ResponseEntity.ok(cartService.findByShoppingCartNumber(shoppingCartNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody ShoppingCart cart){
        try {
            return ResponseEntity.ok(cartService.createCart(cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProductToCart(
            @RequestBody ShoppingCartProduct shoppingCartProduct
    ) {
        try {
            return ResponseEntity.ok(cartService.addProductToCart(shoppingCartProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{productNumber}")
    public ResponseEntity<?> deleteProductFromCart(@RequestBody ShoppingCartProduct shoppingCartProduct,
                                                   @PathVariable Long productNumber) {
        try {
            return ResponseEntity.ok(cartService.removeProductFromCart(shoppingCartProduct, productNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/process")
    public ResponseEntity<?> processCart(@RequestBody CartRequest cartRequest) {
        try {
            OrderDTO order = cartService.processCart(cartRequest);

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}