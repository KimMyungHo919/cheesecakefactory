package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum MenuStatus {
    SALE("SALE"),
    SOLD_OUT("SOLD_OUT");

    private final String status;

    MenuStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static MenuStatus fromStatus(String status) {
        status = status.toUpperCase();
        for (MenuStatus menuStatus : MenuStatus.values()) {
            if (menuStatus.status.equals(status)) {
                return menuStatus;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상태값은 (SALE, SOLD_OUT) 이외의 값이 될 수 없습니다.");
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
