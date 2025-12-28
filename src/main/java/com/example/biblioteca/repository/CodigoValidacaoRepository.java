package com.example.biblioteca.repository;

import com.example.biblioteca.model.usuario.codigo.CodigoValidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CodigoValidacaoRepository extends JpaRepository<CodigoValidacao, UUID> {

    @Query(nativeQuery = true, value = """
            Select * from codigo_validacao c where c.email_destino = :emailDestino order by  c.expiracao DESC limit 1
            """
    )
    Optional<CodigoValidacao> findByEmailDestino(@Param("emailDestino") String emailDestino);
}
