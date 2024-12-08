package com.efactoring.cheesecakefactory.domain.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponseDto {
    private final String status;
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponseDto(String status, int statusCode, String message, LocalDateTime timestamp) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }
}
