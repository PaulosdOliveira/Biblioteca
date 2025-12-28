package com.example.biblioteca.application.ex.exception;

public class ClientrDuplicadoException extends RuntimeException {
    public ClientrDuplicadoException() {
        super("Esse CPF já foi cadastrado");
    }
}
