package com.example.biblioteca.application.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class SecretKeyGenerator {

    private SecretKey secretKey;

    @Value("${variaveis.secret.key.algorithm}")
    private String algorithm;

    @Value("${variaveis.secret.key.encoded}")
    private String encodedString;


    public SecretKey getSecretKey() {
        if (secretKey == null) {
            String[] arrayEncodedString = encodedString.replace("[", "").replace("]", "").split(", ");
            byte[] encoded = new byte[32];
            for (int i = 0; i < arrayEncodedString.length; i++) {
                try {
                    encoded[i] = Byte.parseByte(arrayEncodedString[i]);
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
            this.secretKey = new SecretKeySpec(encoded, algorithm);
        }
        return secretKey;
    }
}
