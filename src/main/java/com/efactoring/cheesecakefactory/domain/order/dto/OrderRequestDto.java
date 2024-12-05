package com.efactoring.cheesecakefactory.domain.order.dto;

import lombok.Getter;

// todo: status enum 형태로 바꾸기

@Getter
public class OrderRequestDto {
    private final Long storeId;
    private final Long menuId;
    private final int quantity;

    public OrderRequestDto(Long storeId, Long menuId, int quantity, String status) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
