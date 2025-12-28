package com.example.biblioteca.validation;

import com.example.biblioteca.application.ex.exception.ClientrDuplicadoException;
import com.example.biblioteca.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ClienteValidation {


    private final ClienteRepository repository;

    public void validar(String cpf) {
        if (repository.existsByCpf(cpf)) throw new ClientrDuplicadoException();
    }
}
