package com.example.biblioteca.application.ex.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException() {
        super("Este email já está sendo utilizado");
    }
}
