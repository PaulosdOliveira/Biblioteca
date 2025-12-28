package com.example.biblioteca.model.usuario.codigo;

import com.example.biblioteca.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class CodigoValidacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 300)
    private String codigo;

    @Column(nullable = false)
    private String emailDestino;

    @Column(nullable = false)
    private LocalDateTime expiracao;


    public CodigoValidacao(String codigo, String emailDestino, long tempoValidade) {
        this.codigo = codigo;
        this.emailDestino = emailDestino;
        this.expiracao = LocalDateTime.now().plusMinutes(tempoValidade);
    }
}
