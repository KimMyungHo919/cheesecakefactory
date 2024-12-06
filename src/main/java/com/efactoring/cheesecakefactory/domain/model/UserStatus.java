package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum UserStatus {
    ACTIVE("ACTIVE"), DELETED("DELETED");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static UserStatus fromString(String status) {
        status = status.toUpperCase();

        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.status.equals(status)) {
                return userStatus;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상태값은 (ACTIVE, DELETED) 이외의 값이 될 수 없습니다.");
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
