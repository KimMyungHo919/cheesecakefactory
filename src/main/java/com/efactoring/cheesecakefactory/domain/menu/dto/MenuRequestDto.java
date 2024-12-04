package com.efactoring.cheesecakefactory.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    @NotBlank(message = "메뉴명을 입력해 주세요")
    private final String name;

    @NotBlank(message = "가격을 입력해 주세요")
    private final long price;

    @NotBlank(message = "상태를 입력해 주세요")
    private final String status;

    public MenuRequestDto(String name, long price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }
}
