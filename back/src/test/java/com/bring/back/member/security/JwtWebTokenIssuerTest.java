package com.bring.back.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

class JwtWebTokenIssuerTest {

    JwtWebTokenIssuer issuer;
    private static String secretKey = "GynmeRtHnk4PwuU16CRFUCU4WGN59AGvARt/ZS0Uqp4=";
    private static String refreshKey = "wuWoEVGATaH5oSLquAiwu6VWhyDF1mP9Qf+kEJT6H+A=";
    private SecretKey secretKeyBytes;
    private SecretKey refreshKeyBytes;

    @BeforeEach
    void setUp() {
        secretKeyBytes = Keys.hmacShaKeyFor(secretKey.getBytes());
        refreshKeyBytes = Keys.hmacShaKeyFor(refreshKey.getBytes());
        issuer = new JwtWebTokenIssuer(secretKey, refreshKey, 10, 30);
    }

    @Test
    @DisplayName("access token를 성공적으로 생성한다")
    void createAccessToken() {
        String jwt = issuer.createAccessToken("임꺽정");
        Claims claims = parseClaims(jwt, secretKeyBytes);

        Assertions.assertThat(claims).isNotNull();
        Assertions.assertThat(claims.getSubject()).isEqualTo("임꺽정");
    }

    @Test
    @DisplayName("refresh token을 성공적으로 생성한다")
    void createRefreshToken() {
        String jwt = issuer.createRefreshToken("임꺽정");
        Claims claims = parseClaims(jwt, refreshKeyBytes);

        Assertions.assertThat(claims).isNotNull();
        Assertions.assertThat(claims.getSubject()).isEqualTo("임꺽정");
    }

    Claims parseClaims(String token, SecretKey key) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }
}
