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
//        newCartItem.setProduct(product);
        newCartItem.setProductNumber(productResponse.getProductNumber());
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);

        cart.getCartItems().add(newCartItem);

        shoppingCartRepository.save(cart);

        return getShoppingCartDTO(cart);
    }

    private ShoppingCartDTO getShoppingCartDTO(ShoppingCart cart) {
        Customer customer = customerServiceFeignClient.findByCustomerId(cart.getCustomerId());

        List<OrderItemDTO> orderItemDTOs = getOrderItemDTOsFromCartItems(cart.getCartItems());

        return ShoppingCartDTO.builder()
                .customer(getCustomerDTO(customer))
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
    public OrderDTO checkoutCart(CartRequest cartRequest, OrderState orderState) throws Exception {
        Long customerId = cartRequest.getCustomerId();
        List<CartRequest.CartItemRequest> cartItems = cartRequest.getCartItems();

//        Optional<Address> optionalAddress = addressRepository.findById(cartRequest.getShippingAddressId());
//        Address shippingAddress = optionalAddress.orElse(new Address());
        Address shippingAddress = new Address();
        shippingAddress.setAddressType(AddressType.SHIPPING);

        Customer customer = customerServiceFeignClient.findByCustomerId(customerId);
        // check if customer is existed. if not, throw exception
        if (customer.getId() == 0) {
            throw new Exception("Customer does not exist. Please register");
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setShippingAddress(shippingAddress);

        if (orderState == null) {
            order.setOrderState(OrderState.NEW);
        } else {
            order.setOrderState(orderState);
        }

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

        ordersRepository.save(order);

        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemDTO> orderItemDTOs = getOrderItemDTOs(order.getOrderItems());

        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setTotalPrice(getTotalPrice(orderItemDTOs));
        orderDTO.setCustomer(getCustomerDTO(customer));
        orderDTO.setOrderState(OrderState.NEW);
        orderDTO.setOrderItems(orderItemDTOs);
        orderDTO.setShippingAddress(getAddressDTO(shippingAddress));

        return orderDTO;
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

    private AddressDTO getAddressDTO(Address shippingAddress) {
        AddressDTO dto = new AddressDTO();
        dto.setId(shippingAddress.getId());
        dto.setPostalCode(shippingAddress.getPostalCode());
        dto.setCity(shippingAddress.getCity());
        dto.setState(shippingAddress.getState());
        dto.setCountry(shippingAddress.getCountry());
        dto.setStreet(shippingAddress.getStreet());
        dto.setAddressType(shippingAddress.getAddressType());
        dto.setCountry(shippingAddress.getCountry());

        return dto;
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

    private CustomerDTO getCustomerDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setEmailAddress(customer.getEmailAddress());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setBillingAddress(getBillingAddressDTO(customer.getBillingAddress()));

        return customerDTO;
    }

    private AddressDTO getBillingAddressDTO(Address billingAddress) {
        if (billingAddress == null) {
            return new AddressDTO();
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressType(billingAddress.getAddressType());
        addressDTO.setId(billingAddress.getId());
        addressDTO.setCity(billingAddress.getCity());
        addressDTO.setState(billingAddress.getState());
        addressDTO.setCountry(billingAddress.getCountry());
        addressDTO.setStreet(billingAddress.getStreet());
        addressDTO.setPostalCode(billingAddress.getPostalCode());

        return addressDTO;
    }
}
