package com.efactoring.cheesecakefactory.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {
    @NotNull(message = "가게 번호를 입력해 주세요.")
    @Min(value = 1, message = "가게 번호는 0이하일 수 없습니다.")
    private final Long storeId;

    @NotNull(message = "메뉴 번호를 입력해 주세요.")
    @Min(value = 1, message = "메뉴 번호는 0이하일 수 없습니다.")
    private final Long menuId;

    @Min(value = 1, message = "개수가 0이하일 수는 없습니다.")
    private final int quantity;

    public OrderRequestDto(Long storeId, Long menuId, int quantity) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
