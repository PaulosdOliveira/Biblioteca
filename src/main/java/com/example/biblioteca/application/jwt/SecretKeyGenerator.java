package com.example.biblioteca.application.jwt;

import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Component
public class SecretKeyGenerator {

    private SecretKey secretKey;

    public SecretKeyGenerator() {
        secretKey = Jwts.SIG.HS256.key().build();
    }
}
