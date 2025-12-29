package com.example.biblioteca.application.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;


@Component
public class SecretKeyGenerator {

    private SecretKey secretKey;

    @Value("variaveis.key.encoded")
    private byte[] encodedKey;


    public SecretKey getSecretKey() {
        if (secretKey == null) {
            System.out.println(Arrays.toString(encodedKey));
            byte [] encoded = new byte[] {96, 36, 50, 82, -10, 107, 127, -5, -12, -53, -5, 90, -71, 100, -104, 124, -45, -96, -98, 86, -33, -61, 48, -121, -51, -89, -93, 15, 40, 16, -102, 118};
            this.secretKey = new SecretKeySpec(encoded, "HmacSHA256");
        }
        return secretKey;
    }
}
