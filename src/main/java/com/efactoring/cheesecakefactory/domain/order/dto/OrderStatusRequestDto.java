package com.efactoring.cheesecakefactory.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {
    @NotNull(message = "메뉴 번호를 입력해 주세요.")
    @Min(value = 1, message = "메뉴 번호는 0이하일 수 없습니다.")
    private final Long menuId;

    public OrderStatusRequestDto(Long menuId) {
        this.menuId = menuId;
    }
}
