package edu.miu.eafinalproject.shoppingcart.data;

import edu.miu.eafinalproject.product.data.CustomerDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShoppingCartDTO {
    private Long shoppingCartNumber;
    private List<OrderItemDTO> orderItems;
    private double totalPrice;
    private CustomerDTO customer;
}
