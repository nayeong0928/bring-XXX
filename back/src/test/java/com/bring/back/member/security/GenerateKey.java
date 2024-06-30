package com.bring.back.member.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GenerateKey {

    @Test
    void generate() throws NoSuchAlgorithmException {
        // 키 생성
        SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//
//        // JWT 생성 예시
//        String jwt = createJwt("user123", secretKey);
//        System.out.println("Generated JWT: " + jwt);
//
//        // 생성된 키를 Base64로 인코딩
//        byte[] secretKeyBytes = secretKey.getEncoded();
//        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKeyBytes);
//
//        System.out.println("base64EncodedKey = " + base64EncodedKey);
    }
}
