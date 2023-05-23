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

    @PostMapping("/checkout/{orderState}")
    public ResponseEntity<?> checkoutCart(@RequestBody CartRequest cartRequest, @PathVariable("orderState") OrderState orderState) {
        try {
            OrderDTO order = cartService.checkoutCart(cartRequest, orderState);

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
