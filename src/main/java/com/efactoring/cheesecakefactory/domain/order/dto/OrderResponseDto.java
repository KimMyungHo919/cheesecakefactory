package com.efactoring.cheesecakefactory.domain.order.dto;

import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private final Long id;
    private final Long storeId;
    private final Long menuId;
    private final String menuName;
    private final Long menuPrice;
    private final Long totalPrice;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderResponseDto(Long id, Long storeId, Long menuId, String menuName, Long menuPrice, Long totalPrice, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public OrderResponseDto(Orders order) {
        this.id = order.getId();
        this.storeId = order.getStore().getId();
        this.menuId = order.getMenu().getId();
        this.menuName = order.getMenu().getName();
        this.menuPrice = order.getMenu().getPrice();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getModifiedAt();
    }
}
