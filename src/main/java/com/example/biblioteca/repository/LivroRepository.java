package com.example.biblioteca.repository;

import com.example.biblioteca.model.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    boolean existsByTituloLikeAndAutorLike(String titulo, String autor);
}
