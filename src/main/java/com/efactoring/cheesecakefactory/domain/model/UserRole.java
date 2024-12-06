package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum UserRole {
    USER("USER"),
    OWNER("OWNER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonCreator
    public static UserRole fromString(String role) {
        role = role.toUpperCase();

        for (UserRole u : values()) {
            if (u.role.equals(role)) {
                return u;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "역할은 (USER, OWNER) 이외의 값이 될 수 없습니다.");
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
