package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReturnStatusCode {
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    OK(200),
    CREATED(201),
    NO_CONTENT(204);

    private final int code;

    ReturnStatusCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
