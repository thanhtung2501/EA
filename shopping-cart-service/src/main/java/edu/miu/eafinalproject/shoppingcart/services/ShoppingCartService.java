package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.shoppingcart.data.OrderDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import edu.miu.eafinalproject.shoppingcart.domain.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDTO addProductToCart(ShoppingCartProduct shoppingCartProduct) throws Exception;
    ShoppingCartDTO removeProductFromCart(ShoppingCartProduct shoppingCartProduct, Long productNumber) throws Exception;
    ShoppingCartDTO findByShoppingCartNumber(Long shoppingCartNumber);
    ShoppingCart createCart(ShoppingCart cart);

    OrderDTO checkoutCart(CartRequest cartRequest, OrderState orderState) throws Exception;

}
