package com.example.biblioteca.application.ex.exception;

public class CodigoInvalidoException extends RuntimeException {
    public CodigoInvalidoException() {
        super("Código inválido");
    }
}
