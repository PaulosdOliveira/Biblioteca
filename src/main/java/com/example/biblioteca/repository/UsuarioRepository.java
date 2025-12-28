package com.example.biblioteca.repository;

import com.example.biblioteca.model.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    @Query("Select u.ativo from Usuario u where u.email = :email")
    Boolean findAtivoByEmail(@Param("email") String email);


    @Query("Select new Usuario(u.email, u.nome, u.ativo) from Usuario u where u.email = :email")
    Optional<Usuario> buscarPorEmail(String email);
}
