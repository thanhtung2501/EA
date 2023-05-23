package edu.miu.eafinalproject.shoppingcart.services;

import edu.miu.eafinalproject.product.data.AddressDTO;
import edu.miu.eafinalproject.product.data.CustomerDTO;
import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.AddressType;
import edu.miu.eafinalproject.product.domain.Customer;
import edu.miu.eafinalproject.product.domain.Product;
import edu.miu.eafinalproject.product.repositories.AddressRepository;
import edu.miu.eafinalproject.product.repositories.CustomerRepository;
import edu.miu.eafinalproject.product.repositories.ProductRepository;
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
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

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

        for (CartItem item : cartItems) {
            if (Objects.equals(item.getProduct().getProductNumber(), productNumber)) {
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
                .filter(item -> Objects.equals(item.getProduct().getProductNumber(), productNumber))
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
        List<OrderItemDTO> orderItemDTOs = getOrderItemDTOsFromCartItems(cart.getCartItems());

        return ShoppingCartDTO.builder()
                .customer(getCustomerDTO(cart.getCustomer()))
                .totalPrice(getTotalPrice(orderItemDTOs))
                .orderItems(orderItemDTOs)
                .shoppingCartNumber(cart.getShoppingCartNumber())
                .build();
    }

    @Override
    @Transactional
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
            ProductDTO productDTO = getProductDTO(cartItem.getProduct());

            OrderItemDTO dto = OrderItemDTO.builder()
                    .product(productDTO)
                    .discountValue(cartItem.getDiscountValue())
                    .quantity(cartItem.getQuantity())
                    .build();

            result.add(dto);
        }

        return result;
    }

    private ProductDTO getProductDTO(Product product) {
        return ProductDTO.builder()
                .price(product.getPrice())
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
    public OrderDTO checkoutCart(CartRequest cartRequest, OrderState orderState) throws Exception {
        Long customerId = cartRequest.getCustomerId();
        List<CartRequest.CartItemRequest> cartItems = cartRequest.getCartItems();

        Optional<Address> optionalAddress = addressRepository.findById(cartRequest.getShippingAddressId());
        Address shippingAddress = optionalAddress.orElse(new Address());
        shippingAddress.setAddressType(AddressType.SHIPPING);

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        // check if customer is existed. if not, throw exception
        if (optionalCustomer.isEmpty()) {
            throw new Exception("Customer does not exist. Please register");
        }

        Customer customer = optionalCustomer.get();

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
            Product product = productRepository.findByProductNumber(productNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product number: " + productNumber));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
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

            total += (price * quantity) - (price * quantity * discountValue / 100);
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
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .discountValue(orderItem.getDiscountValue())
                    .product(getProductDTO(orderItem.getProduct()))
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
