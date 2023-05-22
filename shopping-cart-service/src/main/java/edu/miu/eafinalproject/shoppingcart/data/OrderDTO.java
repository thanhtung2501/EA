package edu.miu.eafinalproject.shoppingcart.data;

import edu.miu.eafinalproject.product.data.AddressDTO;
import edu.miu.eafinalproject.product.data.CustomerDTO;
import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private OrderState orderState;
    private CustomerDTO customer;
    private AddressDTO shippingAddress;
    private LocalDate orderDate;
    private double totalPrice;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
}
