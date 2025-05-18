package com.onebox.cartservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for handling specific exceptions in the application.
 * This class extends RuntimeException and includes an HTTP status code.
 */
public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
