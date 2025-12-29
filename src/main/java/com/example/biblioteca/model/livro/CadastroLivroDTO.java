package com.example.biblioteca.model.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CadastroLivroDTO {

    @NotBlank(message = "Campo obrigatório")
    private final String titulo;

    @NotBlank(message = "Campo obrigatório")
    private final String autor;

    @NotNull(message = "Informe a quantidade de livros disponíveis")
    private final Integer qtdDisponivel;
}
