package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.repositories.ProductRepository;
import edu.miu.eafinalproject.shoppingcart.data.CartItemDTO;
import edu.miu.eafinalproject.shoppingcart.data.ProductDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartDTO;
import edu.miu.eafinalproject.shoppingcart.data.ShoppingCartProduct;
import edu.miu.eafinalproject.shoppingcart.domain.*;
import edu.miu.eafinalproject.shoppingcart.repositories.CartItemRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.OrderItemRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.OrdersRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public ShoppingCartDTO addProductToCart(ShoppingCartProduct shoppingCartProduct) throws Exception {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByShoppingCartNumber(shoppingCartProduct.getShoppingCartNumber());

        // check if the shopping cart has created. if not, throw an exception
        if (optionalShoppingCart.isEmpty()) {
            throw new Exception(String.format("The shopping cart number %s does not exist.", shoppingCartProduct.getShoppingCartNumber()));
        }

        ShoppingCart cart = optionalShoppingCart.orElse(new ShoppingCart());
        List<CartItem> cartItems = cart.getCartItems();

        //check if product is available already
        Long productNumber = shoppingCartProduct.getProductNumber();
        int quantity = shoppingCartProduct.getQuantity();

        for (CartItem item: cartItems) {
            if(Objects.equals(item.getProduct().getProductNumber(), productNumber)){
                item.setQuantity(item.getQuantity() + quantity);
                shoppingCartRepository.save(cart);

                return getShoppingCartDTO(cart);
            }
        }

        // else if product is not available in cart
        Optional<Product> optionalProduct = productRepository.findByProductNumber(productNumber);
        Product productInDB = optionalProduct.orElse(Product.builder().productNumber(productNumber).build());

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productInDB);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);

        // save cart item before adding it to cart to avoid the error "object references an unsaved transient instance - save the transient instance before flushing"
        cartItemRepository.save(newCartItem);

        cart.getCartItems().add(newCartItem);

        shoppingCartRepository.save(cart);

        return getShoppingCartDTO(cart);
    }

    private ShoppingCartDTO getShoppingCartDTO(ShoppingCart cart) {
        return ShoppingCartDTO.builder()
                .cartItems(getCartItemDTOs(cart.getCartItems()))
                .shoppingCartNumber(cart.getShoppingCartNumber())
                .build();
    }

    @Override
    public ShoppingCartDTO findByShoppingCartNumber(Long shoppingCartNumber) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByShoppingCartNumber(shoppingCartNumber);
        ShoppingCart shoppingCart = optionalShoppingCart.orElse(new ShoppingCart());

        List<CartItemDTO> cartItemDTOS = getCartItemDTOs(shoppingCart.getCartItems());

        return ShoppingCartDTO.builder()
                .shoppingCartNumber(shoppingCartNumber)
                .cartItems(cartItemDTOS)
                .build();
    }

    private List<CartItemDTO> getCartItemDTOs(List<CartItem> cartItems) {
        List<CartItemDTO> result = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            ProductDTO productDTO = getProductDTO(cartItem.getProduct());

            CartItemDTO dto = CartItemDTO.builder()
                    .id(cartItem.getId())
                    .item(productDTO)
                    .quantity(cartItem.getQuantity())
                    .build();

            result.add(dto);
        }

        return result;
    }

    private ProductDTO getProductDTO(Product product) {
        return ProductDTO.builder()
                .productName(product.getName())
                .productNumber(product.getProductNumber())
                .build();
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
            long productId = cartItem.getProduct().getId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

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
