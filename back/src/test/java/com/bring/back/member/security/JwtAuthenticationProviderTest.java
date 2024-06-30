package com.bring.back.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

class JwtAuthenticationProviderTest {

    JwtAuthenticationProvider provider;
    private static String secretKey="c2VjcmV0a2V5dXNpbmdpbmF1dGhlbnRpY2F0aW9ucHJvdmlkZXIxMjM0NTY3ODkwaGVsbG93b3JsZA==";
    private SecretKey secretKeyByte;
    
    @BeforeEach
    public void setup(){
        provider=new JwtAuthenticationProvider(secretKey);
        secretKeyByte= Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Test
    @DisplayName("정상적인 토큰으로 인증한 경우 인증이 성공한다")
    void authenticateSuccess() {
        Date dat=new Date();
        Claims claims = Jwts.claims().setSubject("유관순");

        String token=Jwts.builder()
                .signWith(secretKeyByte)
                .setClaims(claims)
                .compact();

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
        Authentication auth=provider.authenticate(jwtAuthenticationToken);

        Assertions.assertThat(auth.getPrincipal()).isEqualTo("유관순");
        Assertions.assertThat(auth.getCredentials()).isEqualTo("");
        Assertions.assertThat(auth.isAuthenticated()).isEqualTo(true);
    }

    @Test
    void supportsFalse() {
        Assertions.assertThat(provider.supports(Authentication.class)).isFalse();
        Assertions.assertThat(provider.supports(AbstractAuthenticationToken.class)).isFalse();
    }

    @Test
    void supportsTrue(){
        Assertions.assertThat(provider.supports(JwtAuthenticationToken.class)).isTrue();
    }

    @Test
    @DisplayName("key가 다른 경우 에러를 던진다")
    void authenticateFailKey(){
        Claims claims=Jwts.claims().setSubject("유관순");
        String wrongKey="c2VjcmV0a2V5dXNpbmdpbmF1dGhlbnRpY2F0aW9ucHJvdmlkZXIxMjM0NTY3ODkwaGVsbG93b3JsZA";
        SecretKey wrongKeyByte=Keys.hmacShaKeyFor(wrongKey.getBytes());

        String token=Jwts.builder()
                .signWith(wrongKeyByte)
                .setClaims(claims)
                .compact();

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);

        Assertions.assertThatThrownBy(()->provider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(JwtInvalidException.class)
                .hasMessage("signatrue key is different");
    }

    @Test
    @DisplayName("만료된 토큰인 경우 에러를 던진다")
    void authenticateFailExpiredJwt(){
        Claims claims=Jwts.claims().setSubject("유관순");
        Date date=new Date();

        String token=Jwts.builder()
                .signWith(secretKeyByte)
                .setClaims(claims)
                .setExpiration(new Date(date.getTime()))
                .compact();

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);

        Assertions.assertThatThrownBy(()->provider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(JwtInvalidException.class)
                .hasMessage("expired token");
    }

    @Test
    @DisplayName("토큰이 null인 경우 에러를 던진다")
    void authenticateNullToken(){
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(null);

        Assertions.assertThatThrownBy(()->provider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(JwtInvalidException.class)
                .hasMessage("using illegal argument like null");
    }

    @Test
    @DisplayName("jwt가 잘못된 형식인 경우 에러를 던진다")
    void authenticateMalformJwt(){
        Claims claims=Jwts.claims().setSubject("유관순");

        String token=Jwts.builder()
                .setClaims(claims)
                .compact();

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);

        Assertions.assertThatThrownBy(()->provider.authenticate(jwtAuthenticationToken))
                .isInstanceOf(JwtInvalidException.class)
                .hasMessage("malformed jwt");
    }
}