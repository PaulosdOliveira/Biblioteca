package com.example.biblioteca.application.ex.exception;

public class EmailNaoValidadoException extends RuntimeException {
    public EmailNaoValidadoException() {
        super("Seu email ainda não foi confirmado, use o código para valida-lo");
    }
}
