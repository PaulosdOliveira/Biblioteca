package com.example.biblioteca.repository;

import com.example.biblioteca.model.emprestimo.LivrosEmprestados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivrosEmprestadosRepository extends JpaRepository<LivrosEmprestados, UUID> {
}
