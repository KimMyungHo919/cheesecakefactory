package com.efactoring.cheesecakefactory.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderStatusRequestDto {
    private final String status;

    public OrderStatusRequestDto(String status) {
        this.status = status;
    }
}
