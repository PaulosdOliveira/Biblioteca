package com.example.biblioteca.application.jwt;

import com.example.biblioteca.model.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Autowired
    private SecretKeyGenerator keyGenerator;

    public String getToken(Usuario usuario) {
        return Jwts.builder().subject(usuario.getEmail())
                .signWith(keyGenerator.getSecretKey())
                .claims(getClamis(usuario))
                .expiration(getExpiration())
                .compact();
    }

    // TEMPO DE VALIDADE DO TOKEN
    private Date getExpiration() {
        var expiration = LocalDateTime.now().plusDays(4)
                .atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(expiration);
    }

    private Map<String, Object> getClamis(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nome", usuario.getNome());
        claims.put("id", usuario.getId());
        claims.put("perfil", usuario.getPerfil());
        return claims;
    }

    public String getEmailByToken(String token) {
        String email = Jwts.parser()
                .verifyWith(keyGenerator.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return email;
    }
}
