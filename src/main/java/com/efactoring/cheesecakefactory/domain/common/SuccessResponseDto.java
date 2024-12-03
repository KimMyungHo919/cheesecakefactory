package com.efactoring.cheesecakefactory.domain.common;

import lombok.Getter;

@Getter
public class SuccessResponseDto {
    private final String status;
    private final int statusCode;
    private final Object data;

    public SuccessResponseDto(String status, int statusCode, Object data) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }
}
