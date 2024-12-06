package com.efactoring.cheesecakefactory.domain.exception;

import com.efactoring.cheesecakefactory.domain.base.ErrorResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final LocalDateTime timestamp = LocalDateTime.now();

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(
            ResponseStatusException exception, HttpServletResponse response
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                exception.getStatusCode().toString().split(" ")[1],
                exception.getStatusCode().value(),
                exception.getReason(),
                timestamp
        );

        return new ResponseEntity<>(errorResponseDto, exception.getStatusCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentExceptionNotFound(
            IllegalArgumentException exception
    ) {
        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                exception.getLocalizedMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidException(
            MethodArgumentNotValidException ex
    ) {
        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                timestamp
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException() {
        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버에 문제가 발생했습니다.",
                timestamp
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
