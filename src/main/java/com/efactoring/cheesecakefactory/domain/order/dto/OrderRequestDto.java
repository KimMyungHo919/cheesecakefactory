package com.efactoring.cheesecakefactory.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private final Long storeId;
    private final Long menuId;
    private final int quantity;

    public OrderRequestDto(Long storeId, Long menuId, int quantity) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
