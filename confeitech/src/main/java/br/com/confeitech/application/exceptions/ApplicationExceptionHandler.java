package br.com.confeitech.application.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationExceptionHandler extends RuntimeException {
    private final HttpStatus status;

    public ApplicationExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApplicationExceptionHandler(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}