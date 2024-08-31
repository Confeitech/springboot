package br.com.confeitech.application.exceptions;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String message) {
        super(message);
    }
}