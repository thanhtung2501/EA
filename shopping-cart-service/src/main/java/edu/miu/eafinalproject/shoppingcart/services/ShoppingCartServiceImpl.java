package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.external.CustomerServiceFeignClient;
import edu.miu.eafinalproject.external.ProductServiceFeignClient;
import edu.miu.eafinalproject.product.data.*;
import edu.miu.eafinalproject.shoppingcart.data.*;
import edu.miu.eafinalproject.shoppingcart.data.request.CartRequest;
import edu.miu.eafinalproject.shoppingcart.domain.*;
import edu.miu.eafinalproject.shoppingcart.repositories.OrdersRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    @Autowired
    private CustomerServiceFeignClient customerServiceFeignClient;

    @Autowired
    private OrdersRepository ordersRepository;

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
            if(Objects.equals(item.getProductNumber(), productNumber)){
                item.setQuantity(item.getQuantity() + quantity);
                shoppingCartRepository.save(cart);

                return getShoppingCartDTO(cart);
            }
        }

        // else if product is not available in cart
        ProductResponse productResponse = productServiceFeignClient.findByProductNumber(productNumber);

        CartItem newCartItem = new CartItem();
        newCartItem.setProductNumber(productResponse.getProductNumber());
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);

        cart.getCartItems().add(newCartItem);

        shoppingCartRepository.save(cart);

        return getShoppingCartDTO(cart);
    }

    @Override
    public ShoppingCartDTO removeProductFromCart(ShoppingCartProduct shoppingCartProduct, Long productNumber) throws Exception {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByShoppingCartNumber(shoppingCartProduct.getShoppingCartNumber());

        // Check if the shopping cart has been created. If not, throw an exception.
        if (optionalShoppingCart.isEmpty()) {
            throw new Exception(String.format("The shopping cart number %s does not exist.", shoppingCartProduct.getShoppingCartNumber()));
        }

        ShoppingCart cart = optionalShoppingCart.get();
        List<CartItem> cartItems = cart.getCartItems();

        // Find the cart item with the specified product number
        Optional<CartItem> optionalCartItem = cartItems.stream()
                .filter(item -> Objects.equals(item.getProductNumber(), productNumber))
                .findFirst();

        // If the cart item is found, remove it from the cart
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItems.remove(cartItem);
            shoppingCartRepository.save(cart);
            return getShoppingCartDTO(cart);
        } else {
            throw new Exception(String.format("The product with number %d is not found in the cart.", productNumber));
        }
    }

    private ShoppingCartDTO getShoppingCartDTO(ShoppingCart cart) {
        CustomerResponse customerResponse = customerServiceFeignClient.findByCustomerId(cart.getCustomerId());

        List<OrderItemDTO> orderItemDTOs = getOrderItemDTOsFromCartItems(cart.getCartItems());

        return ShoppingCartDTO.builder()
                .customer(getCustomerDTO(customerResponse))
                .totalPrice(getTotalPrice(orderItemDTOs))
                .orderItems(orderItemDTOs)
                .shoppingCartNumber(cart.getShoppingCartNumber())
                .build();
    }

    @Override
    public ShoppingCartDTO findByShoppingCartNumber(Long shoppingCartNumber) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByShoppingCartNumber(shoppingCartNumber);
        ShoppingCart shoppingCart = optionalShoppingCart.orElse(new ShoppingCart());

        List<OrderItemDTO> cartItemDTOS = getOrderItemDTOsFromCartItems(shoppingCart.getCartItems());

        return ShoppingCartDTO.builder()
                .shoppingCartNumber(shoppingCartNumber)
                .orderItems(cartItemDTOS)
                .build();
    }

    private List<OrderItemDTO> getOrderItemDTOsFromCartItems(List<CartItem> cartItems) {
        List<OrderItemDTO> result = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            ProductResponse productResponse = productServiceFeignClient.findByProductNumber(cartItem.getProductNumber());
            ProductDTO productDTO = getProductDTO(productResponse);

            OrderItemDTO dto = OrderItemDTO.builder()
                    .product(productDTO)
                    .discountValue(cartItem.getDiscountValue())
                    .quantity(cartItem.getQuantity())
                    .build();

            result.add(dto);
        }

        return result;
    }

    private ProductDTO getProductDTO(ProductResponse productResponse) {
        return ProductDTO.builder()
                .price(productResponse.getPrice())
                .productName(productResponse.getName())
                .productNumber(productResponse.getProductNumber())
                .build();
    }

    @Override
    public ShoppingCart createCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public OrderDTO processCart(CartRequest cartRequest) throws Exception {
        long customerId = cartRequest.getCustomerId();
        long addressId = cartRequest.getShippingAddressId();
        List<CartRequest.CartItemRequest> cartItems = cartRequest.getCartItems();

        AddressResponse shippingAddress = customerServiceFeignClient.findByAddressId(addressId);
        shippingAddress.setAddressType(AddressType.SHIPPING);

        CustomerResponse customerResponse = customerServiceFeignClient.findByCustomerId(customerId);
        // check if customer is existed. if not, throw exception
        if (customerResponse.getId() == 0) {
            throw new Exception("Customer does not exist. Please register");
        }

        OrderState orderState = cartRequest.getOrderState();
        if (orderState == null) {
            orderState = OrderState.NEW;
        }

        Orders order = new Orders();
        order.setCustomer(customerResponse);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDate.now());
        order.setOrderState(orderState);

        for (CartRequest.CartItemRequest cartItem : cartItems) {
            long productNumber = cartItem.getProductNumber();
            ProductResponse productResponse = productServiceFeignClient.findByProductNumber(productNumber);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductNumber(productResponse.getProductNumber());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscountValue(cartItem.getDiscountValue());
            orderItem.setOrders(order);

            order.getOrderItems().add(orderItem);
        }

        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemDTO> orderItemDTOs = getOrderItemDTOs(order.getOrderItems());

        double totalPrice = getTotalPrice(orderItemDTOs);

        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setCustomer(getCustomerDTO(customerResponse));
        orderDTO.setOrderState(orderState);
        orderDTO.setOrderItems(orderItemDTOs);
        orderDTO.setShippingAddress(shippingAddress);

        order.setTotalPrice(totalPrice);
//        ordersRepository.save(order);

        // save shopping cart
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByShoppingCartNumber(cartRequest.getShoppingCartNumber());
        ShoppingCart shoppingCart = optionalShoppingCart.orElse(new ShoppingCart());
        shoppingCart.setCartItems(getCartItems(cartRequest));
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setCustomerId(customerId);

        shoppingCartRepository.save(shoppingCart);
        ordersRepository.save(order);

        return orderDTO;
    }

    private List<CartItem> getCartItems(CartRequest cartRequest) {
        List<CartItem> result = new ArrayList<>();

        List<CartRequest.CartItemRequest> cartItems = cartRequest.getCartItems();

        for (CartRequest.CartItemRequest cartItemRequest : cartItems) {
            CartItem cartItem = new CartItem();

            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setDiscountValue(cartItemRequest.getDiscountValue());
            cartItem.setProductNumber(cartItemRequest.getProductNumber());

            result.add(cartItem);
        }

        return result;
    }

    private double getTotalPrice(List<OrderItemDTO> orderItemDTOs) {
        double total = 0.0;

        for (OrderItemDTO itemDTO : orderItemDTOs) {
            double price = itemDTO.getProduct().getPrice();
            int quantity = itemDTO.getQuantity();
            double discountValue = itemDTO.getDiscountValue();

            total += (price * quantity) - (price * quantity * discountValue/100);
        }

        return total;
    }

    private List<OrderItemDTO> getOrderItemDTOs(List<OrderItem> orderItems) {
        List<OrderItemDTO> result = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            ProductResponse productResponse = productServiceFeignClient.findByProductNumber(orderItem.getProductNumber());

            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .discountValue(orderItem.getDiscountValue())
                    .product(getProductDTO(productResponse))
                    .quantity(orderItem.getQuantity())
                    .build();

            result.add(orderItemDTO);
        }

        return result;
    }

    private CustomerDTO getCustomerDTO(CustomerResponse customerResponse) {
        if (customerResponse == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerResponse.getId());
        customerDTO.setEmailAddress(customerResponse.getEmailAddress());
        customerDTO.setFirstName(customerResponse.getFirstName());
        customerDTO.setLastName(customerResponse.getLastName());
        customerDTO.setBillingAddress(customerResponse.getBillingAddress());

        return customerDTO;
    }

}
