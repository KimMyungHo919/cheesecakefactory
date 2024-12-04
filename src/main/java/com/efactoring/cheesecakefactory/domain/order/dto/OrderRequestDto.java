package com.efactoring.cheesecakefactory.domain.order.dto;

import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    private final int quantity;
    private final Long totalPrice;
    private final String status;

    public OrderRequestDto(Orders orders) {
        this.quantity = orders.getQuantity();
        this.totalPrice = orders.getTotalPrice();
        this.status = orders.getStatus();
    }
}
