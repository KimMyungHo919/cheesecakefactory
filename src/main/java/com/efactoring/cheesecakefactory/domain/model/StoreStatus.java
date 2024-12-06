package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum StoreStatus {
    ACTIVE("ACTIVE"),
    CLOSE("CLOSE");

    private final String status;

    StoreStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static StoreStatus fromString(String status) {
        status = status.toUpperCase();
        for (StoreStatus s : StoreStatus.values()) {
            if (s.status.equals(status)) {
                return s;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상태값은 (ACTIVE, CLOSE) 이외의 값이 될 수 없습니다.");
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
