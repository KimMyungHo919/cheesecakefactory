package com.efactoring.cheesecakefactory.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderStatusRequestDto {
    private final Long menuId;

    public OrderStatusRequestDto(Long menuId) {
        this.menuId = menuId;
    }
}
