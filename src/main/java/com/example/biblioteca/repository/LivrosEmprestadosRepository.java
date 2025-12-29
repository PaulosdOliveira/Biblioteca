package com.example.biblioteca.repository;

import com.example.biblioteca.model.emprestimo.LivroEmprestadoDTO;
import com.example.biblioteca.model.emprestimo.LivrosEmprestados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface LivrosEmprestadosRepository extends JpaRepository<LivrosEmprestados, UUID> {

    @Query("Select new com.example.biblioteca.model.emprestimo.LivroEmprestadoDTO(l.id, l.cliente.nome, l.livro.titulo, l.status) from LivrosEmprestados l where l.cliente.id = :id")
    List<LivroEmprestadoDTO> getLivrosEmprestadosByIdCliente(@Param("id") UUID id);

    @Query("Select count(l.id) from LivrosEmprestados l where l.cliente.id = :idCliente and l.status = 'PENDENTE'")
    int countLivrosEmprestados(@Param("idCliente") UUID idCliente);


    @Transactional
    @Modifying
    @Query("Delete from LivrosEmprestados l where l.cliente.id = :id")
    void deleteByClienteId(@Param("id") UUID id);
}
