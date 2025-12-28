package com.example.biblioteca.validation;

import com.example.biblioteca.application.ex.exception.EmailDuplicadoException;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidation {

    @Autowired
    private UsuarioRepository repository;


    public void validar(String email) {
        Boolean ativo = repository.findAtivoByEmail(email);
        if (ativo != null && ativo) throw new EmailDuplicadoException();

    }
}
