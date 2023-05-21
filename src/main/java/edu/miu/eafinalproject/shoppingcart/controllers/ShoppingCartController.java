package edu.miu.eafinalproject.shoppingcart.controllers;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.domain.CartItem;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.Orders;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;
import edu.miu.eafinalproject.shoppingcart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/{shoppingCartNumber}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Long shoppingCartNumber){
        return ResponseEntity.ok(cartService.findByShoppingCartNumber(shoppingCartNumber));
    }

    @PostMapping
    public ShoppingCart createCart(@RequestBody ShoppingCart cart){
        return cartService.createCart(cart);
    }

    @PostMapping("/product")
    public ResponseEntity<ShoppingCartDTO> addProductToCart(
            @RequestBody ShoppingCartProduct shoppingCartProduct
    ) throws Exception {
        return ResponseEntity.ok(cartService.addProductToCart(shoppingCartProduct));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Orders> checkoutCart(@RequestBody CartRequest cartRequest) {
        Customer customer = cartRequest.getCustomer();
        Address shippingAddress = cartRequest.getShippingAddress();
        List<CartItem> cartItems = cartRequest.getCartItems();

        Orders order = cartService.checkoutCart(customer, shippingAddress, cartItems);

        return ResponseEntity.ok(order);
    }
}
