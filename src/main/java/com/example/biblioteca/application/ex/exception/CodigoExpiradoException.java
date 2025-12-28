package com.example.biblioteca.application.ex.exception;

public class CodigoExpiradoException extends RuntimeException {
    public CodigoExpiradoException() {
        super("Código expirado, solicite outro");
    }
}
