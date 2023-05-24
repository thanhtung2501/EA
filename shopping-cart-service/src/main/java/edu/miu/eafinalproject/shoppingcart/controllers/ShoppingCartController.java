package edu.miu.eafinalproject.shoppingcart.controllers;

import edu.miu.eafinalproject.shoppingcart.data.OrderDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import edu.miu.eafinalproject.shoppingcart.services.ShoppingCartService;
import edu.miu.eafinalproject.shoppingcart.utils.ResponseWrapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Qualifier("shoppingCartServiceImpl")
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private ResponseWrapperUtils responseWrapperUtils;

    @GetMapping("/{shoppingCartNumber}")
    public ResponseEntity<?> getShoppingCart(@PathVariable("shoppingCartNumber") Long shoppingCartNumber) {
        ShoppingCartDTO shoppingCartDTO = cartService.findByShoppingCartNumber(shoppingCartNumber);
        if (shoppingCartDTO == null) {
            String errorMessage = String.format("The shopping cart number %s does not exist.", shoppingCartNumber);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseWrapperUtils
                            .getResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    errorMessage,
                                    null));
        } else {
            return ResponseEntity.ok(responseWrapperUtils
                    .getResponseObject(HttpStatus.OK.value(), "Success", shoppingCartDTO));
        }
    }

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody ShoppingCart cart) {
        try {
            ShoppingCart cartResponse = cartService.createCart(cart);
            return ResponseEntity.ok(responseWrapperUtils
                    .getResponseObject(HttpStatus.OK.value(),
                            "Cart created successfully.",
                            cartResponse));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapperUtils
                    .getResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ex.getMessage(),
                            null));
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProductToCart(@RequestBody ShoppingCartProduct shoppingCartProduct) {
        try {
            ShoppingCartDTO shoppingCartDTO = cartService.addProductToCart(shoppingCartProduct);
            return ResponseEntity.ok(responseWrapperUtils.getResponseObject(HttpStatus.OK.value(),
                    "Product added successfully.",
                    shoppingCartDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseWrapperUtils.getResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ex.getMessage(),
                            null));
        }
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProductFromCart(@RequestBody ShoppingCartProduct shoppingCartProduct) {
        try {
            ShoppingCartDTO shoppingCartDTO = cartService.removeProductFromCart(shoppingCartProduct);
            return ResponseEntity.ok(responseWrapperUtils.getResponseObject(HttpStatus.OK.value(),
                    "Product deleted successfully.", shoppingCartDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseWrapperUtils.getResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ex.getMessage(),
                            null));
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