package com.example.biblioteca.validation;

import com.example.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class LivroValidator {

    private final LivroRepository repository;


    public void validar(String titulo, String autor) {
        if (repository.existsByTituloLikeAndAutorLike(titulo, autor)) throw new RuntimeException("Livro já existe");
    }
}
