package com.akshay.order.orderline;

import com.akshay.order.model.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "customer_line")
public class OrderLine {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}
