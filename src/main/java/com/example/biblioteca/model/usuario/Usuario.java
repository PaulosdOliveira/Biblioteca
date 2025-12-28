package com.example.biblioteca.model.usuario;

import com.example.biblioteca.model.usuario.enums.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;


    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 300, unique = true)
    private String senha;

    private boolean ativo;


    public Usuario(CadastroUsuarioDTO dadosCadastrais) {
        BeanUtils.copyProperties(dadosCadastrais, this);
        this.perfil = Perfil.USER;
        this.ativo = false;
    }

    public Usuario(CadastroUsuarioDTO dadosCadastrais, Perfil perfil) {
        BeanUtils.copyProperties(dadosCadastrais, this);
        this.perfil = perfil;
        this.ativo = false;
    }

    public Usuario(String email, String nome, boolean ativo) {
        this.email = email;
        this.nome = nome;
        this.ativo = ativo;
    }
}
