package edu.miu.eafinalproject.shoppingcart.data;

import edu.miu.eafinalproject.product.data.AddressDTO;
import edu.miu.eafinalproject.product.data.CustomerDTO;
import edu.miu.eafinalproject.shoppingcart.domain.OrderState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private OrderState orderState;
    private CustomerDTO customer;
    private AddressDTO shippingAddress;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
}
