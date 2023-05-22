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
import edu.miu.eafinalproject.shoppingcart.repositories.CartItemRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.OrderItemRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.OrdersRepository;
import edu.miu.eafinalproject.shoppingcart.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EntityManager entityManager;

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
                    .product(productDTO)
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
    public OrderDTO checkoutCart(CartRequest cartRequest) throws Exception {
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
        order.setOrderState(OrderState.NEW);

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

        orderItemRepository.saveAll(order.getOrderItems());

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomer(getCustomerDTO(customer));
        orderDTO.setOrderState(OrderState.NEW);
        orderDTO.setOrderItems(getOrderItemDTOs(order.getOrderItems()));
        orderDTO.setShippingAddress(getAddressDTO(shippingAddress));

        return orderDTO;
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
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setDiscountValue(orderItem.getDiscountValue());
            orderItemDTO.setProduct(getProductDTO(orderItem.getProduct()));
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setId(orderItem.getId());

            result.add(orderItemDTO);
        }

        return result;
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setEmailAddress(customerDTO.getEmailAddress());
        customerDTO.setFirstName(customerDTO.getFirstName());
        customerDTO.setLastName(customerDTO.getLastName());
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
