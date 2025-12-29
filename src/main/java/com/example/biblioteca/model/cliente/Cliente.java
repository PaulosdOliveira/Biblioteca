package com.example.biblioteca.model.cliente;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 18)
    private String contato;

    public Cliente(CadastroClienteDTO dadosCadastrais) {
        BeanUtils.copyProperties(dadosCadastrais, this);
    }

    public Cliente(UUID id) {
        this.id = id;
    }
}
