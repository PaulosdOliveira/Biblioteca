package com.example.biblioteca.model.emprestimo;

import com.example.biblioteca.model.cliente.Cliente;
import com.example.biblioteca.model.emprestimo.enums.Status;
import com.example.biblioteca.model.livro.Livro;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
public class LivrosEmprestados {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn()
    private Livro livro;

    @ManyToOne
    @JoinColumn()
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private LocalDateTime dataHoraEmprestimo;


}
