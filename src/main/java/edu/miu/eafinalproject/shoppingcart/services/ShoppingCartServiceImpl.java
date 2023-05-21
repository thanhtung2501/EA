package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.repositories.ProductRepository;
import edu.miu.eafinalproject.shoppingcart.data.CartItem;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.domain.*;
import edu.miu.eafinalproject.shoppingcart.repositories.OrderItemRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.OrdersRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public ShoppingCart addProductToCart(ShoppingCartProduct shoppingCartProduct) {
        ShoppingCart cart = shoppingCartRepository.findByShoppingCartNumber(shoppingCartProduct.getShoppingCartNumber());
        List<CartLine> cartLines = cart.getCartLines();

        //check if product is available already
        Product product = shoppingCartProduct.getProduct();
        int quantity = shoppingCartProduct.getQuantity();

        for (CartLine cl: cartLines) {
            if(Objects.equals(cl.getProduct().getProductNumber(), product.getProductNumber())){
                cl.setQuantity(cl.getQuantity() + quantity);
                return shoppingCartRepository.save(cart);
            }
        }

        // else if product is not available in cart
        CartLine newCartLine = new CartLine(product, quantity);
        cartLines.add(newCartLine);

        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart findByShoppingCartNumber(Long shoppingCartNumber) {
        return shoppingCartRepository.findByShoppingCartNumber(shoppingCartNumber);
    }

    @Override
    public ShoppingCart createCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public Orders checkoutCart(Customer customer, Address shippingAddress, List<CartItem> cartItems) {
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setShippingAddress(shippingAddress);
        order.setOrderState(OrderState.NEW);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + cartItem.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscountValue(cartItem.getDiscountValue());
            orderItem.setOrders(order);

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);

        ordersRepository.save(order);

        return order;
    }
}
