package com.efactoring.cheesecakefactory.domain.menu.dto;

import com.efactoring.cheesecakefactory.domain.model.MenuStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotEmpty(message = "메뉴명을 입력해 주세요.")
    private final String name;

    @Min(value = 1, message = "금액은 0이하가 될 수 없습니다.")
    private final long price;

    @NotNull(message = "메뉴 상태를 입력해 주세요.")
    private final MenuStatus status;

    public MenuRequestDto(String name, long price, MenuStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }
}
