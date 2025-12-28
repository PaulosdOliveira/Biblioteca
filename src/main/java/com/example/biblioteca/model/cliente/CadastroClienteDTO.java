package com.example.biblioteca.model.cliente;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;


@Data
public class CadastroClienteDTO {

    @CPF(message = "Informe um CPF válido")
    @NotBlank(message = "Campo obrigatório")
    private String cpf;

    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @Email(message = "Informe um email válido")
    @NotBlank(message = "Informe o email do cliente")
    private String email;

    @NotBlank(message = "Informe o contato do cliente")
    private String contato;
}
