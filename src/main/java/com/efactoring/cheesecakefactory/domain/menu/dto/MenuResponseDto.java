package com.efactoring.cheesecakefactory.domain.menu.dto;

import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.model.MenuStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuResponseDto {
    private final long id;
    private final String name;
    private final long price;
    private final MenuStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getModifiedAt();
    }
}
