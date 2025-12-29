package com.example.biblioteca.model.emprestimo;

import com.example.biblioteca.model.emprestimo.enums.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class LivroEmprestadoDTO {
    private final UUID idEmprestimo;
    private final String cliente;
    private final String titulo;
    private final Status status;
}
