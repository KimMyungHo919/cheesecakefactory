package com.efactoring.cheesecakefactory.domain.exception;

import com.efactoring.cheesecakefactory.domain.base.ErrorResponseDto;
import com.efactoring.cheesecakefactory.domain.model.ReturnStatusCode;
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
        ReturnStatusCode statusCode = ReturnStatusCode.NOT_FOUND;

        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                statusCode.name(),
                statusCode.getCode(),
                exception.getLocalizedMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidException(
            MethodArgumentNotValidException ex
    ) {
        ReturnStatusCode statusCode = ReturnStatusCode.BAD_REQUEST;

        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                statusCode.name(),
                statusCode.getCode(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                timestamp
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException() {
        ReturnStatusCode statusCode = ReturnStatusCode.INTERNAL_SERVER_ERROR;

        ErrorResponseDto errorResponseData = new ErrorResponseDto(
                statusCode.name(),
                statusCode.getCode(),
                "서버에 문제가 발생했습니다.",
                timestamp
        );

        return new ResponseEntity<>(errorResponseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
