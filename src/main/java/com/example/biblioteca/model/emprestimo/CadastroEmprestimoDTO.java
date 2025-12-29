package com.example.biblioteca.model.emprestimo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CadastroEmprestimoDTO {
    @NotNull(message = "Campo obrigatório")
    private final UUID idLivro;
    @NotNull(message = "Campo obrigatório")
    private final UUID idCliente;
}
