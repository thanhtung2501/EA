package edu.miu.eafinalproject.shoppingcart.domain;

import edu.miu.eafinalproject.product.domain.Address;
import edu.miu.eafinalproject.product.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public void placeOrder() {
        if (this.orderState == OrderState.NEW) {
            this.orderState = OrderState.PLACED;
        } else {
            throw new IllegalStateException("Order can only be placed from NEW state.");
        }
    }

    public void processOrder() {
        if (this.orderState == OrderState.PLACED) {
            this.orderState = OrderState.PROCESSED;
        } else {
            throw new IllegalStateException("Order can only be processed from PLACED state.");
        }
    }

    public void shipOrder() {
        if (this.orderState == OrderState.PROCESSED) {
            this.orderState = OrderState.SHIPPED;
        } else {
            throw new IllegalStateException("Order can only be shipped from PROCESSED state.");
        }
    }

    public void deliverOrder() {
        if (this.orderState == OrderState.SHIPPED) {
            this.orderState = OrderState.DELIVERED;
        } else {
            throw new IllegalStateException("Order can only be delivered from SHIPPED state.");
        }
    }

    public void returnOrder() {
        if (this.orderState == OrderState.DELIVERED) {
            this.orderState = OrderState.RETURNED;
        } else {
            throw new IllegalStateException("Order can only be returned from DELIVERED state.");
        }
    }
}
