package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.shoppingcart.data.OrderDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDTO addProductToCart(ShoppingCartProduct shoppingCartProduct) throws Exception;
    ShoppingCartDTO removeProductFromCart(ShoppingCartProduct shoppingCartProduct) throws Exception;
    ShoppingCartDTO findByShoppingCartNumber(Long shoppingCartNumber);
    ShoppingCart createCart(ShoppingCart cart) throws Exception;

    OrderDTO processCart(CartRequest cartRequest) throws Exception;

}
