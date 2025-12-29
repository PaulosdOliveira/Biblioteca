package com.example.biblioteca.model.livro;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 75)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String autor;

    @Column(nullable = false)
    private Integer qtdDisponivel;


    public Livro(CadastroLivroDTO dadosCadastrais) {
        BeanUtils.copyProperties(dadosCadastrais, this);
    }

    public Livro(UUID id) {
        this.id = id;
    }
}
