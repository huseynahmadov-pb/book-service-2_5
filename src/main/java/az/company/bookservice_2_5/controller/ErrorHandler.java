package az.company.bookservice_2_5.controller;

import az.company.bookservice_2_5.exception.NotFoundException;
import az.company.bookservice_2_5.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static az.company.bookservice_2_5.model.enums.ErrorMessage.INTERNAL_ERROR;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> validationErrors = new ArrayList<>();
        exception.getFieldErrors().forEach(error -> validationErrors.add(error.getDefaultMessage()));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message("Validation failed for one or more fields.")
                .timestamp(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("ILLEGAL_ARGUMENT")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(INTERNAL_ERROR.name())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }
}
