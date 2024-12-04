package com.efactoring.cheesecakefactory.domain.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private final Long id;
    private final Long storeId;
    private final Long menuId;
    private final String menuName;
    private final int menuPrice;
    private final Long totalPrice;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderResponseDto(Long id, Long storeId, Long menuId, String menuName, int menuPrice, Long totalPrice, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.storeId = storeId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
