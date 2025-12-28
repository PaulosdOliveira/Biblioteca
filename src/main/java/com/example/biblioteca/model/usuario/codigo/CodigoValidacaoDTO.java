package com.example.biblioteca.model.usuario.codigo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CodigoValidacaoDTO {

    @Email(message = "Digite um email válido")
    private final String email;

    @Size(max = 6, min = 6, message = "O código precisa ter 6 digitos")
    private final String codigo;
}
