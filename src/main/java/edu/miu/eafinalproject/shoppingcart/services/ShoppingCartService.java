package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.domain.CartItem;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.domain.Orders;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCartDTO addProductToCart(ShoppingCartProduct shoppingCartProduct) throws Exception;
    ShoppingCartDTO findByShoppingCartNumber(Long shoppingCartNumber);
    ShoppingCart createCart(ShoppingCart cart);

    Orders checkoutCart(Customer customer, Address shippingAddress, List<CartItem> cartItems);

}
